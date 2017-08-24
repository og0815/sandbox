package de.ltux.webbox;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author olive
 */
@Named
@ViewScoped
public class TreeController implements Serializable {

    private final Logger L = LoggerFactory.getLogger(TreeController.class);

    @Getter
    private TreeNode root;

    @Setter
    @Getter
    private TreeNode selectedNode;

    @Setter
    @Getter
    private String prefix = "";

    @Inject
    private Inspector inspector;

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

    public List<String> inspect() {
        L.info("Inspecting " + prefix);
        return inspector.jndi(prefix).stream().map(np -> "(name=" + np.getName() + ")").collect(Collectors.toList());
    }
}
