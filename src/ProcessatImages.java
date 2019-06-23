package src;

public class ProcessatImages{
    protected int [][] img;
    protected int llindar;

    public ProcessatImages(int [][] img, int llindar){
        this.img=img;
        this.llindar=llindar;
    }


    //Passa un pixel en forma de (fila,columna) a la un int
    public int pixel2Int(int f, int c){
        return f*img[f].length + c;
    }

    public  void netejar(){
        try{
            final int BLACK=0;
            if (img == null) return;
            MFSet api = new MFSet(img[0].length * img[1].length);
            System.out.println(api.initialize());
            System.out.println("Find: " + api.find(img[0][0]));
            System.out.println("Find pixel: " + api.find(pixel2Int(0,0))); // Esto debería dar -1, da 0
            System.out.println("Find: " + api.find(img[1][1]));
            System.out.println("Find pixel: " + api.find(pixel2Int(1,1))); // Esto debería dar -1, da 1
            
            for(int f = 0; f < img[0].length -1 ; f++) {
                //System.out.println(f + " / " + img[0].length);
                for (int c  = 0; c < img[1].length - 1; c++) {
                      if(img[f][c]==BLACK && img[f][c] >= 0){
                        if(f>0 && c>0){
                            int pixel = pixel2Int(f,c);
                            if(img[f-1][c+1] == BLACK){
                                api.merge(pixel, pixel2Int(f-1,c+1));
                            } // NordEst
                            if(img[f][c+1] == BLACK) api.merge(pixel, pixel2Int(f,c+1)); // Est
                            if(img[f+1][c+1] == BLACK) api.merge(pixel, pixel2Int(f+1,c+1)); // SudEst
                            if(img[f+1][c] == BLACK) api.merge(pixel, pixel2Int(f+1,c)); // Sud
                            int size = api.findCardinalitat(pixel);
                            if(size < llindar) img[f][c] = 255;
                        }
                    }
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }


    public int [][] getImage(){
        return img;
    }
}