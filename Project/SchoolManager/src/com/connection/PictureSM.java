package com.connection;

import com.string.Strings;
import com.user.User;
import com.user.UserDao;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;

public class PictureSM {
    private static Image dispPic;
    
    public static Image getProfilePicture(ImageView imgview, User currUser) throws SQLException, IOException {
        Blob profilePicBlob = null;
        profilePicBlob = UserDao.getProfilePictureBlob(currUser.getUserID());

        if(profilePicBlob != null) {
            InputStream is = profilePicBlob.getBinaryStream();
            BufferedImage buffedImage = ImageIO.read(is);

            WritableImage proPic = SwingFXUtils.toFXImage(buffedImage, null);
            imgview.setImage(proPic);
        }

        dispPic = imgview.getImage();
        
        return dispPic;
    }
    
    public static void clearProfilePicture() {
        dispPic = null;
    }
    
}
