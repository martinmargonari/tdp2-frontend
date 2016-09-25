package com.example.margonari.tdp2_frontend.services;

import com.example.margonari.tdp2_frontend.domain.Course;
import com.example.margonari.tdp2_frontend.rest_dto.CoursesDTO;

import java.util.List;

/**
 * Created by luis on 25/09/16.
 */

public class CourseFullDataServices extends AbstractServices {

    private static final String service_name = "course";


    public Course getCourseBy(String course_id) {
        String coursesQuery = this.getQueryBy(course_id);
        System.out.println("Course query" +coursesQuery);
        CoursesDTO coursesDTO = (CoursesDTO) geDataOftDTO(coursesQuery, CoursesDTO.class);
        return coursesDTO.getData().get( 0 );
    }

    @Override
    protected String getQueryBy(String... params) {
        String course_id = params[0];

        String url = urlBase;
        StringBuffer urlStringBuffer = new StringBuffer(url);
        urlStringBuffer.append(service_name);
        urlStringBuffer.append("?");
        urlStringBuffer.append("api_token=");
        urlStringBuffer.append(api_security);
        urlStringBuffer.append("&course_id=");
        urlStringBuffer.append(course_id);

        return urlStringBuffer.toString();
    }
}