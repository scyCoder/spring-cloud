package com.jrx.config;

import com.jrx.model.GradeMessage;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.util.HashMap;


/**
 * 学生成绩任务
 *
 * @author Administrator
 */
@Configuration
public class GradeMessageJobConfig extends BaseConfig {


    @Bean
    public Job gradeMessageJob() {
        return jobBuilderFactory.get("gradeMessageJob102")
                .start(gradeStep())
//                .split(new SimpleAsyncTaskExecutor())
//                .add(writeFileFlow())
//                .end()    异步执行多个step，报错：Cannot open an already opened ItemReader, call close first
                .next(writeFileStep())
                .build();
    }

    /**
     * 创建一个Flow对象，用来存储step集合，在这里是异步里
     * 把读到的数据分别导入数据库和文件中
     *
     * @return
     */
    @Bean
    public Flow writeFileFlow() {

        return new FlowBuilder<Flow>("writeFileFlow")
                .start(writeFileStep())
                .build();
    }


    @Bean
    public Step gradeStep() {
        return stepBuilderFactory.get("gradeStep")
                .<GradeMessage, GradeMessage>chunk(2)
                .reader(gradeMessageReader())
                .writer(gradeMessageWriter())
                .build();
    }

    @Bean
    public JdbcPagingItemReader<GradeMessage> gradeMessageReader() {
        JdbcPagingItemReader<GradeMessage> reader = new JdbcPagingItemReader();
        reader.setDataSource(dataSource);
        reader.setFetchSize(2);
        // 指定SQL语句
        MySqlPagingQueryProvider sql = new MySqlPagingQueryProvider();
        sql.setSelectClause("student_id,exam_id,SUM(exam_score) as total,AVG(exam_score) as aver");
        sql.setFromClause("from student_exam");
        sql.setGroupClause("group by student_id");
        HashMap<String, Order> hashMap = new HashMap(1);
        hashMap.put("exam_id", Order.ASCENDING);
        sql.setSortKeys(hashMap);
        reader.setQueryProvider(sql);
        reader.setRowMapper((resultSet, i) -> {
            GradeMessage gradeMessage = new GradeMessage();
            gradeMessage.setAverGrade(resultSet.getDouble("aver"));
            gradeMessage.setTotalGrade(resultSet.getDouble("total"));
            gradeMessage.setExamId(resultSet.getInt("exam_id"));
            gradeMessage.setStudentId(resultSet.getInt("student_id"));
            return gradeMessage;
        });
        return reader;
    }

    @Bean
    public JdbcBatchItemWriter<GradeMessage> gradeMessageWriter() {
        JdbcBatchItemWriter writer = new JdbcBatchItemWriter();
        writer.setDataSource(dataSource);
        writer.setSql("insert into student_grade(student_id,exam_id,aver,total) values (:studentId,:examId," +
                ":averGrade,:totalGrade)");
        // 将读取的数据与StudentMessage对象映射
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<GradeMessage>());
        return writer;
    }

    @Bean
    public Step writeFileStep() {
        return stepBuilderFactory.get("writeFileStep")
                .<GradeMessage, GradeMessage>chunk(2)
                .reader(gradeMessageReader())
                .writer(writeFileWriter())
                .build();
    }

    @Bean
    public FlatFileItemWriter<? super GradeMessage> writeFileWriter() {
        FlatFileItemWriter<GradeMessage> writer = new FlatFileItemWriter();
        writer.setResource(new FileSystemResource("F:\\JRXworkspace\\spring-project\\spring-batch\\src\\main" +
                "\\resources\\jobfile\\student-grade.txt"));
        writer.setAppendAllowed(true);
        // 第一种方式
       /* writer.setLineAggregator(new LineAggregator<GradeMessage>() {
            ObjectMapper mapper = new ObjectMapper();

            @Override
            public String aggregate(GradeMessage gradeMessage) {
                String gradeMessageStr = null;
                try {
                    gradeMessageStr = mapper.writeValueAsString(gradeMessage);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return gradeMessageStr;
            }
        });
        try {
            writer.afterPropertiesSet();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        // 第二种方式
        writer.setLineAggregator(new DelimitedLineAggregator<GradeMessage>() {{
            setDelimiter("|");
            setFieldExtractor(new BeanWrapperFieldExtractor<GradeMessage>() {{
                setNames(new String[]{
                        "studentId", "examId", "averGrade", "totalGrade"
                });
            }});
        }});
        return writer;
    }
}
