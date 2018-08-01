package com.example.finalproject.services;

import com.example.finalproject.entities.MarkEntity;
import com.example.finalproject.entities.StudentEntity;
import com.example.finalproject.entities.SubjectEntity;
import com.example.finalproject.entities.TeacherEntity;
import com.example.finalproject.models.EmailObject;

public interface EmailService {

	void sendTemplateMessagesVoucher (StudentEntity student, TeacherEntity teacher, SubjectEntity subject, MarkEntity mark) throws Exception;
	
}
