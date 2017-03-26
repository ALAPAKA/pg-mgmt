package com.pg.mgmt.app.context;

import com.google.appengine.api.utils.SystemProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * Created by Siva on 3/26/2017.
 */
public class CustomGAEXmlWebApplicationContext extends XmlWebApplicationContext {
    private static final Logger logger = LoggerFactory.getLogger(CustomGAEXmlWebApplicationContext.class);

    protected void initBeanDefinitionReader(XmlBeanDefinitionReader beanDefinitionReader) {
        super.initBeanDefinitionReader(beanDefinitionReader);
        if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
            beanDefinitionReader.setValidating(false);
            beanDefinitionReader.setNamespaceAware(true);
            logger.info("CustomGAEXmlWebApplicationContext loaded... in production... XML validation disabled.");
        }
        logger.info("CustomGAEXmlWebApplicationContext loaded...");
    }
}
