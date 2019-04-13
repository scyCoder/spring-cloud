package com.jrx.config;

import com.jrx.model.ExamMessage;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.BindException;

/**
 * 考试信息任务
 *
 * @Author: sunchuanyin
 * @Date: 2019/4/10 12:20
 * @Version 1.0
 */
@Configuration
public class ExamMessageJobConfig extends BaseConfig {


    /**
     * 创建从文件中读取学生信息的job任务
     *
     * @return
     */
    @Bean
    public Job readExamMessageJob() {
        return jobBuilderFactory.get("readExamMessageJob4")
                .start(readExamMessageStep())
                .build();

    }

    /**
     * 创建Step,任务执行的过程
     *
     * @return
     */
    @Bean
    public Step readExamMessageStep() {
        return stepBuilderFactory.get("readExamMessageStep")
                // 每读取2条数据，处理一次
                .<ExamMessage, ExamMessage>chunk(2)
                .reader(itemReaderExamMessage())
                .writer(itemWriterExamMessage())
                .build();
    }

    /**
     * 将读取的数据写到数据库中
     *
     * @return
     */
    @Bean
    public JdbcBatchItemWriter<? super ExamMessage> itemWriterExamMessage() {
        JdbcBatchItemWriter writer = new JdbcBatchItemWriter();
        writer.setDataSource(dataSource);
        writer.setSql("insert into student_exam(exam_id,exam_course,exam_score,exam_time,student_id) values (:examId," +
                ":examCourse,:examScore,:examTime,:studentId)");
        // 将读取的数据与StudentMessage对象映射
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<ExamMessage>());
        return writer;
    }


    /**
     * 从文件中读取数据
     *
     * @return
     */
    @Bean
    @StepScope
    public FlatFileItemReader<ExamMessage> itemReaderExamMessage() {

        FlatFileItemReader reader = new FlatFileItemReader();
        reader.setResource(new ClassPathResource("jobfile/student-exam.txt"));
        // 跳过第一条数据
        reader.setLinesToSkip(1);
        // 创建解析数据对象
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        // 设置字段名称
        tokenizer.setNames(new String[]{"exam_id", "exam_course", "exam_score", "exam_time", "student_id"});
        // 创建映射对象
        DefaultLineMapper<ExamMessage> mapper = new DefaultLineMapper();
        mapper.setLineTokenizer(tokenizer);
        // 把解析的一行数据映射成对象
        mapper.setFieldSetMapper(new FieldSetMapper<ExamMessage>() {
            @Override
            public ExamMessage mapFieldSet(FieldSet fieldSet) throws BindException {
                ExamMessage examMessage = new ExamMessage();
                examMessage.setExamId(fieldSet.readInt("exam_id"));
                examMessage.setExamCourse(fieldSet.readString("exam_course"));
                examMessage.setExamTime(fieldSet.readDate("exam_time"));
                examMessage.setExamScore(fieldSet.readDouble("exam_score"));
                examMessage.setStudentId(fieldSet.readInt("student_id"));

                return examMessage;
            }
        });
        mapper.afterPropertiesSet();
        reader.setLineMapper(mapper);
        return reader;
    }
}
