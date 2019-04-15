package edu.gatech.CQLStorage.listener;

import java.io.IOException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import edu.gatech.CQLStorage.entity.CQL;
import edu.gatech.CQLStorage.repo.CQLRepository;

@Component
public class StartupDeafultScriptInitializer implements ApplicationListener<ApplicationReadyEvent>{
	Logger log = LoggerFactory.getLogger(StartupDeafultScriptInitializer.class);
	PathMatchingResourcePatternResolver resolver;
	@Autowired
	CQLRepository repository;
	
	@EventListener(ApplicationReadyEvent.class)
	public void loadCQLScripts() {
	}

	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {
		log.debug("Running loadCQLScripts.");
		resolver = new PathMatchingResourcePatternResolver(this.getClass().getClassLoader());
		try {
			Resource[] resources = resolver.getResources("classpath*:/*.cql");
			for(Resource resource: resources) {
				log.debug("importing cql file:"+resource.getURL().toString());
				Scanner scanner = new Scanner(resource.getInputStream());
				scanner.useDelimiter("\\A");
				String body = scanner.hasNext() ? scanner.next() : "";
				scanner.close();
				String fileName = resource.getFilename();
				CQL entity = new CQL();
				entity.setName(fileName.substring(0, fileName.length()-4)); //Lose the extension
				entity.setBody(body);
				repository.save(entity);
				resource.getFile().delete(); //Clean out cql file once it's been put into Database
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
