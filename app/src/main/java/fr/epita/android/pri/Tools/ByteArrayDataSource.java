package fr.epita.android.pri.Tools;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by sadekseridj on 25/11/2017.
 */

public class ByteArrayDataSource implements javax.activation.DataSource {
    private byte[] data;
    private String type;

    public ByteArrayDataSource(byte[] data, String type)
    {
        super();
        this.data = data;
        this.type = type;
    }

    public ByteArrayDataSource(byte[] data)
    {
        super();
        this.data = data;
    }

    public String getContentType()
    {
        if (type == null)
            return "application/octet-stream";
        return type;
    }

    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(data);
    }

    public String getName() {
        return "ByteArrayDataSource";
    }

    public OutputStream getOutputStream() throws IOException {
        throw new IOException("Not Supported");
    }


}
