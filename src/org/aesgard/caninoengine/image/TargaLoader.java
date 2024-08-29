package org.aesgard.caninoengine.image;

import java.io.DataInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class TargaLoader{

  private static final ClassLoader fileLoader    = TargaLoader.class.getClassLoader();

  private static DataInputStream dataInputStream = null;

  private static byte  FHimageIDLength   =    0,
                       FHcolorMapType    =    0, // 0 = no pallete

                       FHimageType       =    0; // uncompressed RGB=2, uncompressed grayscale=3

  private static short FHcolorMapOrigin  =    0,
                       FHcolorMapLength  =    0;
  private static byte  FHcolorMapDepth   =    0;
  private static short FHimageXOrigin    =    0,
                       FHimageYOrigin    =    0,
                       FHwidth           =    0,
                       FHheight          =    0;
  private static byte  FHbitCount        =    0, // 16,24,32

                       FHimageDescriptor =    0; // 24 bit = 0x00, 32-bit=0x08

  private static       ByteBuffer byteBuffer   = null;

  public TargaLoader(){}

  public ByteBuffer getData(){
    return byteBuffer;
  }

  public int getWidth(){
    return FHwidth;
  }

  public int getHeight(){
    return FHheight;
  }

  public int getBPP(){
    return FHbitCount;
  }

  public  void printHeaders() {
    System.out.println("-----------------------------------");
    System.out.println("File Header");
    System.out.println("-----------------------------------");
    System.out.println("      SpriteImage ID Length:"+FHimageIDLength);
    System.out.println("       Color Map Type:"+FHcolorMapType);
    System.out.println("           SpriteImage Type:"+FHimageType);
    System.out.println("     Color Map Origin:"+FHcolorMapOrigin);
    System.out.println("     Color Map Length:"+FHcolorMapLength);
    System.out.println(" Color Map Entry Size:"+FHcolorMapDepth);
    System.out.println("       SpriteImage X Origin:"+FHimageXOrigin);
    System.out.println("       SpriteImage Y Origin:"+FHimageYOrigin);
    System.out.println("                Width:"+FHwidth);
    System.out.println("               Height:"+FHheight);
    System.out.println("                  BBP:"+FHbitCount);
    System.out.println("     SpriteImage Descriptor:"+FHimageDescriptor);
  }

  public void load(String filename) {
    // reset everything

    FHimageIDLength   = 0;
    FHcolorMapType    = 0;
    FHimageType       = 0;
    FHcolorMapOrigin  = 0;
    FHcolorMapLength  = 0;
    FHcolorMapDepth   = 0;
    FHimageXOrigin    = 0;
    FHimageYOrigin    = 0;
    FHwidth           = 0;
    FHheight          = 0;
    FHbitCount        = 0;
    FHimageDescriptor = 0;

    InputStream fileInputStream = fileLoader.getResourceAsStream(filename);

    try{
      if(fileInputStream == null)
       fileInputStream = new java.io.FileInputStream(filename);

      dataInputStream = new DataInputStream(fileInputStream);

       // Kewl spaceship

      FHimageIDLength   = dataInputStream.readByte();
      FHcolorMapType    = dataInputStream.readByte();
      FHimageType       = dataInputStream.readByte();
      FHcolorMapOrigin  = readShort(dataInputStream.readByte(),
                                    dataInputStream.readByte());
      FHcolorMapLength  = readShort(dataInputStream.readByte(),
                                    dataInputStream.readByte());
      FHcolorMapDepth   = dataInputStream.readByte();
      FHimageXOrigin    = readShort(dataInputStream.readByte(),
                                    dataInputStream.readByte());
      FHimageYOrigin    = readShort(dataInputStream.readByte(),
                                    dataInputStream.readByte());
      FHwidth           = readShort(dataInputStream.readByte(),
                                    dataInputStream.readByte());
      FHheight          = readShort(dataInputStream.readByte(),
                                    dataInputStream.readByte());
      FHbitCount        = dataInputStream.readByte();
      FHimageDescriptor = dataInputStream.readByte();

      if(FHbitCount == 16){
        load16bitsTGA();
        printHeaders();
        return;
      }

      if(FHimageType!=2 && FHimageType!=3) {
        if(FHimageType == 10)
          loadCompressed();
        return;
      }

      int    bytesPerPixel = (FHbitCount/8);
      byte[] data          =  new byte[FHwidth*FHheight*bytesPerPixel];

      dataInputStream.readFully(data);

      byteBuffer = ByteBuffer.allocateDirect(data.length)
                             .order(ByteOrder.nativeOrder())
                             .put(data);
      byteBuffer.flip();

      fileInputStream.close();
      dataInputStream.close();
      data = null;
    }
    catch(Exception x){
      System.out.println(x.getMessage());
    }
  }

  public void load16bitsTGA(){
    try{
      byteBuffer = ByteBuffer.allocateDirect(FHwidth*FHheight*3)
                             .order(ByteOrder.nativeOrder());
      short RGBshort = 0;
      int   size     = FHwidth*FHheight;

      for(int i = 0; i < size; i++){
        RGBshort = readShort(dataInputStream.readByte(),
                             dataInputStream.readByte());
        byteBuffer.put((byte)( (RGBshort        & 0x1f) << 3))
                  .put((byte)(((RGBshort >>  5) & 0x1f) << 3))
                  .put((byte)(((RGBshort >> 10) & 0x1f) << 3));
      }
      dataInputStream.close();
      byteBuffer.flip();
    }
    catch(Exception x){
      x.printStackTrace();
      System.out.println(x.getMessage());
    }
  }

  public void loadCompressed() {
    printHeaders();
    int bytesPerPixel = (FHbitCount/8);

    byteBuffer = ByteBuffer.allocateDirect(FHwidth*FHheight*bytesPerPixel)
                           .order(ByteOrder.nativeOrder());

    int pixelcount   = FHwidth*FHheight,
        currentbyte  = 0,
        currentpixel = 0;

    byte[] colorbuffer = new byte[bytesPerPixel];

    try{
      do{
        int chunkheader = 0;
        chunkheader =  dataInputStream.readUnsignedByte();
        if(chunkheader < 128){
          chunkheader++;
          for(short counter = 0; counter < chunkheader; counter++){

            dataInputStream.read(colorbuffer);
            byteBuffer.put(colorbuffer[0])
                      .put(colorbuffer[1])
                      .put(colorbuffer[2]);

            if(bytesPerPixel == 4)
               byteBuffer.put(dataInputStream.readByte());

            currentbyte += bytesPerPixel;
            currentpixel++;
            if(currentpixel > pixelcount)
              throw new Exception("Too many pixels read");
          }
        }
        else{
          chunkheader -= 127;
          dataInputStream.read(colorbuffer);

          for(short counter = 0; counter < chunkheader; counter++){
            byteBuffer.put(colorbuffer[0])
                      .put(colorbuffer[1])
                      .put(colorbuffer[2]);

            if(bytesPerPixel == 4)
               byteBuffer.put(dataInputStream.readByte());

            currentbyte += bytesPerPixel;
            currentpixel++;
            if(currentpixel > pixelcount)
              throw new Exception("Too many pixels read");
          }
        }
      } while (currentpixel < pixelcount);

      byteBuffer.flip();

      dataInputStream.close();
    }
    catch(Exception x){
      x.printStackTrace();
      System.out.println(x.getMessage());
    }
  }
  
  private short readShort(byte b1, byte b2){
    int s1 = (b1 & 0xFF),
        s2 = (b2 & 0xFF) << 8;
    return ((short)(s1 | s2));
  }
}     