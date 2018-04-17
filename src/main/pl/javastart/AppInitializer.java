package pl.javastart;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class AppInitializer implements WebApplicationInitializer
{
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException
    {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.setServletContext(servletContext);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(ctx);

        ServletRegistration.Dynamic dispatcherConfig = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcherConfig.addMapping("/");
    }
}
