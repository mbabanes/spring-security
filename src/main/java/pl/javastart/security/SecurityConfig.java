package pl.javastart.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        JdbcUserDetailsManagerConfigurer<AuthenticationManagerBuilder> builder = auth.jdbcAuthentication();

        builder.dataSource(dataSource);

        JdbcUserDetailsManager userDetailsManager = builder.getUserDetailsService();

        userDetailsManager.setUsersByUsernameQuery("select username, password, enabled from accounts where username=?");
        userDetailsManager.setAuthoritiesByUsernameQuery("select username, authority from roles where username=?");
        userDetailsManager.setCreateUserSql("insert into accounts (username, password, enabled) values(?,?,?)");
        userDetailsManager.setCreateAuthoritySql("insert into roles (username, authority) values (?,?)");

        builder.withUser("administrator").password("{noop}AdMin!@3").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin();
    }
}
