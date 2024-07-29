//package io.github.zaragozamartin91.staticpropertyanalyser;
//
//import org.springframework.core.env.MutablePropertySources;
//import org.springframework.core.env.PropertySourcesPropertyResolver;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.support.ResourcePropertySource;
//
//public class PropertyLoaderExample {
//
//    public static void main(String[] args) throws Exception {
//        // Create a new MutablePropertySources object
//        MutablePropertySources propertySources = new MutablePropertySources();
//
//        // Load properties from application.properties into a PropertySource
//        ResourcePropertySource applicationProperties = new ResourcePropertySource(new ClassPathResource("application.properties"));
//
//        // Load properties from application-staging.properties into a PropertySource
//        ResourcePropertySource stagingProperties = new ResourcePropertySource(new ClassPathResource("application-staging.properties"));
//
//        // Add the PropertySources to the MutablePropertySources object
//        // The last added PropertySource has the highest precedence
//        propertySources.addFirst(applicationProperties);
//        propertySources.addFirst(stagingProperties);
//
//        // Create a new PropertySourcesPropertyResolver with the MutablePropertySources
//        PropertySourcesPropertyResolver resolver = new PropertySourcesPropertyResolver(propertySources);
//
//        // Use the resolver to get the value of a property
//        String myProperty = resolver.getProperty("my.property");
//
//        System.out.println(myProperty);
//    }
//}