package com.policia.codigopolicia.zxing.barcodescanner.camera;

import com.policia.codigopolicia.zxing.barcodescanner.SourceData;

/**
 * Callback for camera previews.
 */
public interface PreviewCallback {
    void onPreview(SourceData sourceData);
    void onPreviewError(Exception e);
}
