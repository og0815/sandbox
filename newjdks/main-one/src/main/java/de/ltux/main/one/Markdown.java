/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.main.one;

import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.profiles.pegdown.Extensions;
import com.vladsch.flexmark.profiles.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;
import de.ltux.pojo.three.Paper;

/**
 *
 * @author oliver.guenther
 */
public class Markdown {

    static final DataHolder OPTIONS = PegdownOptionsAdapter.flexmarkOptions(
            Extensions.ALL
    );

    static final MutableDataSet FORMAT_OPTIONS = new MutableDataSet();

    static {
        // copy extensions from Pegdown compatible to Formatting, but leave the rest default
        FORMAT_OPTIONS.set(Parser.EXTENSIONS, OPTIONS.get(Parser.EXTENSIONS));
    }

    static final Parser PARSER = Parser.builder().build();
    static final Formatter RENDERER = Formatter.builder(FORMAT_OPTIONS).build();

    public static void main(String[] args) {
        var p = new Paper();
        p.setColor("white");
        p.setContent("Bla Bla");
        p.setWrittenOn(true);

        System.out.println(p);
        System.out.println(p.toMarkdown());

        Document document = PARSER.parse("Heading\n===\n");
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        System.out.println(renderer.render(document));
        // use the PARSER to parse pegdown indentation rules and RENDERER to render CommonMark
        System.out.println(renderer.render(PARSER.parse(p.toMarkdown())));
    }

}
