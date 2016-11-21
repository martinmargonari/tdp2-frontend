package com.example.margonari.tdp2_frontend.services;

import android.util.Log;

import com.example.margonari.tdp2_frontend.domain.Course;
import com.example.margonari.tdp2_frontend.rest_dto.CoursesDTO;

/**
 * Created by luis on 25/09/16.
 */

public class CourseFullDataServicesWithUnits extends AbstractServices {

    private static final String service_name = "course/session";

    public Course getCourseBy(String course_id, String session_id) {
        String coursesQuery = this.getQueryBy(course_id, session_id);
        Log.d("Query "+this.getClass().getName(),coursesQuery);
        CoursesDTO coursesDTO = (CoursesDTO) geDataOftDTO(coursesQuery, CoursesDTO.class);
        return coursesDTO.getData().get( 0 );
    }

    @Override
    protected String getQueryBy(String... params) {
        String course_id = params[0];
        String session_id=params[1];
        String url = urlBase;
        StringBuffer urlStringBuffer = new StringBuffer(url);
        urlStringBuffer.append(service_name);
        urlStringBuffer.append("?");
        urlStringBuffer.append("api_token=");
        urlStringBuffer.append(api_security);
        urlStringBuffer.append("&course_id=");
        urlStringBuffer.append(course_id);
        urlStringBuffer.append("&session_id=");
        urlStringBuffer.append(course_id);

        return urlStringBuffer.toString();
    }
}