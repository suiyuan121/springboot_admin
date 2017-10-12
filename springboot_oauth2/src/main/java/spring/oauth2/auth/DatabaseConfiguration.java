package spring.oauth2.auth;


import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.boot.jta.XADataSourceWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by Jerry on 2017/7/10.
 */
@Configuration
public class DatabaseConfiguration {

    @Autowired
    @Bean
    public DataSource dataSource(DataSourceProperties properties, XADataSourceWrapper wrapper) throws Exception {
        MysqlXADataSource xzDataSource = new MysqlXADataSource();
        xzDataSource.setPinGlobalTxToPhysicalConnection(true);

        MutablePropertyValues values = new MutablePropertyValues();
        values.add("user", properties.determineUsername());
        values.add("password", properties.determinePassword());
        values.add("url", properties.determineUrl());
        values.addPropertyValues(properties.getXa().getProperties());
        new RelaxedDataBinder(xzDataSource).withAlias("user", "username").bind(values);

        return wrapper.wrapDataSource(xzDataSource);
    }

}
