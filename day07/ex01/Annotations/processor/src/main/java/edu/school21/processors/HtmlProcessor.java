package edu.school21.processors;

import com.google.auto.service.AutoService;
import edu.school21.annotations.HtmlForm;
import edu.school21.annotations.HtmlInput;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Set;

@SupportedAnnotationTypes("edu.school21.annotations.*")
@AutoService(Processor.class)
public class HtmlProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(HtmlForm.class)) { // form classes
            HtmlForm htmlForm = element.getAnnotation(HtmlForm.class);
            if (htmlForm != null) {
                try (PrintWriter out = new PrintWriter(htmlForm.fileName())) {
                    StringBuilder result = new StringBuilder();
                    result.append("<form action = \"");
                    result.append(htmlForm.action());
                    result.append("\" method = \"");
                    result.append(htmlForm.method());
                    result.append("\">\n");

                    for (Element enclosedElement : element.getEnclosedElements()) { // form inputs
                        HtmlInput htmlInput = enclosedElement.getAnnotation(HtmlInput.class);
                        if (htmlInput != null) {
                            result.append("<input type = \"");
                            result.append(htmlInput.type());
                            result.append("\" name = \"");
                            result.append(htmlInput.name());
                            result.append("\" placeholder = \"");
                            result.append(htmlInput.placeholder());
                            result.append("\">\n");
                        }
                    }
                    result.append("<input type = \"submit\" value = \"Send\">\n" +
                            "</form>\n");
                    out.print(result.toString());
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return true;
    }

}
