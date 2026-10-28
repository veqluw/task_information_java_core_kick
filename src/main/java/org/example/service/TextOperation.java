package org.example.service;

import org.example.entity.Text;

public interface TextOperation<R> {
    R execute(Text text);
}
