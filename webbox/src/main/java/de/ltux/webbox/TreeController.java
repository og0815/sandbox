package de.ltux.webbox;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.TreeNode;

/**
 *
 * @author olive
 */
@Named
@ViewScoped
public class TreeController implements Serializable {

    @Getter
    private TreeNode root;

    @Setter
    @Getter
    private TreeNode selectedNode;

    @PostConstruct
    public void init() {
        root = DocumentService.createDocuments();
    }

    public List<Document> getSelections() {
        if (selectedNode == null) {
            return null;
        }
        return selectedNode.getChildren().stream().map(n -> ((Document) n.getData())).collect(Collectors.toList());
    }
}
