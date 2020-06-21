package net.skhu.e05photo;

import java.io.File;

public interface OnFileShareListener {
    void onShareRequested(int index, File file);
}
