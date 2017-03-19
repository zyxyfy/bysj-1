package com.newview.bysj.service.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;

import com.newview.bysj.domain.HolidayAndVacation;
import com.newview.bysj.service.HolidayAndVacationService;

public class TestHolidayAndVacationService {
    HolidayAndVacationService holidayAndVacationService;

    @Before
    public void init() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationcontext.xml");
        holidayAndVacationService = (HolidayAndVacationService) ac.getBean("holidayAndVacationService");
    }

    @Test
    public void save() {
        HolidayAndVacation hv = new HolidayAndVacation();
        hv.setDescription("holidayandvacation");
        holidayAndVacationService.save(hv);
    }

    @Test
    public void getPageByTutor() {
        Page<HolidayAndVacation> hv = holidayAndVacationService.getPageByTutor(1, 5, "holiday");
        for (HolidayAndVacation holidayAndVacation : hv) {
            System.out.println(holidayAndVacation.getDescription());
        }
    }


}
