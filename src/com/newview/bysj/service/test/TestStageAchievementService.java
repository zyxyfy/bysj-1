package com.newview.bysj.service.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newview.bysj.domain.GraduateProject;
import com.newview.bysj.service.GraduateProjectService;
import com.newview.bysj.service.StageAchievementService;

public class TestStageAchievementService {

    StageAchievementService stageAchievementService;
    GraduateProjectService graduateProjectService;

    @Before
    public void init() {
        @SuppressWarnings("resource")
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        stageAchievementService = (StageAchievementService) ac.getBean("stageAchievementService");
        graduateProjectService = (GraduateProjectService) ac.getBean("graduateProjectService");
    }

	/*@Test
    public void test() {
		// fail("Not yet implemented");
		GraduateProject graduateProject = new GraduateProject();
		graduateProjectService.save(graduateProject);
		List<GraduateProject> l = new ArrayList<>();
		l.add(graduateProject);
		stageAchievementService.getPageByGraduateProjects(l, 1, 2);
	}
*/

}
