package com.jrx.config;

import com.jrx.model.StudentMessage;
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
 * 学生信息任务
 *
 * @Author: sunchuanyin
 * @Date: 2019/4/10 12:20
 * @Version 1.0
 */
@Configuration
public class StudentMessageJobConfig extends BaseConfig {

    /**
     * 创建从文件中读取学生信息的job任务
     *
     * @return
     */
    @Bean
    public Job readStudentMessageJob() {
        return jobBuilderFactory.get("readStudentMessageJob1")
                .start(readStudentMessageStep())
                .build();

    }

    /**
     * 创建Step,任务执行的过程
     *
     * @return
     */
    @Bean
    public Step readStudentMessageStep() {
        return stepBuilderFactory.get("readStudentMessageStep")
                // 每读取2条数据，处理一次
                .<StudentMessage, StudentMessage>chunk(2)
                .reader(itemReaderStudentMessage())
                .writer(itemWriterStudentMessage())
                .build();
    }

    /**
     * 将读取的数据写到数据库中
     *
     * @return
     */
    @Bean
    public JdbcBatchItemWriter<? super StudentMessage> itemWriterStudentMessage() {
        JdbcBatchItemWriter writer = new JdbcBatchItemWriter();
        writer.setDataSource(dataSource);
        writer.setSql("insert into student_message(id,class_id,name,sex,age) values (:id,:classId,:name,:sex,:age)");
        // 将读取的数据与StudentMessage对象映射
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<StudentMessage>());
        return writer;
    }


    /**
     * 从文件中读取数据
     *
     * @return
     */
    @Bean
    @StepScope
    public FlatFileItemReader<StudentMessage> itemReaderStudentMessage() {

        FlatFileItemReader reader = new FlatFileItemReader();
        reader.setResource(new ClassPathResource("jobfile/student-message.txt"));
        // 跳过第一条数据
        reader.setLinesToSkip(1);
        // 创建解析数据对象
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        // 设置字段名称
        tokenizer.setNames(new String[]{"id", "classId", "name", "sex", "age"});
        // 创建映射对象
        DefaultLineMapper<StudentMessage> mapper = new DefaultLineMapper();
        mapper.setLineTokenizer(tokenizer);
        // 把解析的一行数据映射成对象
        mapper.setFieldSetMapper(new FieldSetMapper<StudentMessage>() {
            @Override
            public StudentMessage mapFieldSet(FieldSet fieldSet) throws BindException {
                StudentMessage studentMessage = new StudentMessage();
                studentMessage.setId(fieldSet.readInt("id"));
                studentMessage.setClassId(fieldSet.readInt("classId"));
                studentMessage.setName(fieldSet.readString("name"));
                studentMessage.setSex(fieldSet.readString("sex"));
                studentMessage.setAge(fieldSet.readInt("age"));
                return studentMessage;
            }
        });
        mapper.afterPropertiesSet();
        reader.setLineMapper(mapper);
        return reader;
    }
}
