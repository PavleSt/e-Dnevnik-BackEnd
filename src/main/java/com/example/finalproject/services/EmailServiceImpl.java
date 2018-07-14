package com.example.finalproject.services;

import java.time.LocalDate;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.finalproject.entities.LectureEntity;
import com.example.finalproject.entities.MarkEntity;
import com.example.finalproject.entities.ParentEntity;
import com.example.finalproject.entities.StudentEntity;
import com.example.finalproject.entities.SubjectEntity;
import com.example.finalproject.entities.TeacherEntity;
import com.example.finalproject.entities.dto.MarkDTO;
import com.example.finalproject.models.EmailObject;
import com.example.finalproject.repositories.ParentRepository;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	public JavaMailSender emailSender;
	@Autowired
	public ParentRepository pareRepo;

	@Override
	public void sendSimpleMessage(EmailObject object) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(object.getTo());
		message.setSubject(object.getSubject());
		message.setText(object.getText());
		emailSender.send(message);
		
	}

	@Override
	public void sendTemplateMessagesVoucher(StudentEntity student, TeacherEntity teacher, SubjectEntity subject, MarkEntity mark) 
			throws Exception {
		MimeMessage mail = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mail, true);
		
		StudentEntity stud = student;
		ParentEntity parent = stud.getParent();
		TeacherEntity teac = teacher;
		SubjectEntity subj = subject;
		MarkEntity mar = mark;
		
		helper.setTo(parent.getEmail());
		helper.setSubject("School report");
		String text = "<html><body><table border=2" + "style='border:2px solid black'>"  
						+ "<tr><td><b>" + "Student" + "</b></td>"
						+ "<td><b>" + "Mark" + "</b></td>"
						+ "<td><b>" + "Subject" + "</b></td>"
						+ "<td><b>" + "Teacher" + "</b></td></tr>"
						+ "<tr><td>" + stud.getFirstName() + " " + stud.getLastName() + "</td>"
						+ "<td>" + mar.getMarkNo() + "</td>"
						+ "<td>" + subj.getSubjectName() + "</td>"
						+ "<td>" + teac.getFirstName() + teac.getLastName() + "</td></tr>"
						+ "</table></body></html>";	
		
						
		helper.setText(text, true);
		emailSender.send(mail);
		
	}

	@Override
	public void sendMessageWithAttachment(EmailObject object, String pathToAttachment) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
