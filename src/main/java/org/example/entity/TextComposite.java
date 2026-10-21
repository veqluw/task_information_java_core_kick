package org.example.entity;

import java.util.ArrayList;
import java.util.List;

public abstract class TextComposite implements TextComponent {

    private final List<TextComponent> children = new ArrayList<>();

    public void add(TextComponent component) {
        if (component != null) {
            children.add(component);
        }
    }

    public List<TextComponent> getChildren() {
        return children.stream().toList();
    }

    @Override
    public String getContent() {
        StringBuilder result = new StringBuilder();

        for (TextComponent child : children) {
            if (!result.isEmpty()) {
                result.append(getChildSeparator());
            }

            result.append(child.getContent());
        }

        return result.toString();
    }

    protected abstract String getChildSeparator();
}