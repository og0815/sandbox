package de.ltux.jsf;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import de.ltux.api.TheService;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Named
@RequestScoped
public class SampleBean {

    @Inject
    @ConfigProperty(name = "message")
    private String message;

    @EJB
    private TheService s;

    public String getMessage() {
        return this.message;
    }

    public String getPaperToHtml() {
        Parser parser = Parser.builder().build();
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(parser.parse(s.getPaper().toMarkdown() + "\n\n" + s.getStone().toMarkdown() + "\n\n" + s.getPerson().toMarkdown()));
    }
}
