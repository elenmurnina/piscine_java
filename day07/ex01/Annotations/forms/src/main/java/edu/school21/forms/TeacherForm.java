package edu.school21.forms;

import edu.school21.annotations.HtmlForm;
import edu.school21.annotations.HtmlInput;

@HtmlForm(fileName = "teacher_form.html", action = "/teachers", method = "get")
public class TeacherForm {
    @HtmlInput(type = "text", name = "id", placeholder = "Type your id")
    private String id;

    @HtmlInput(type = "text", name = "code", placeholder = "Enter code")
    private String code;
}
