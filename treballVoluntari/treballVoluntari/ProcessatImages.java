package treballVoluntari;

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
    
          for(int f = 0; f < img.length -1 ; f++) {
              //System.out.println(f + " / " + img.length);
              for (int c  = 0; c < img[0].length - 1; c++) {
                  System.out.println(c + " / " + img[0].length + f + " / " + img.length);
                  if(img[f][c]==BLACK){
                      System.out.println(api.find(img[f][c]));
                      if(f>0 && c>0){
                          if(img[f-1][c+1] == BLACK){ 
                              api.merge(this.pixel2Int(f, c), this.pixel2Int(f-1, c+1));
                            } // NordEst
                          if(img[f][c+1] == BLACK) api.merge(this.pixel2Int(f, c), this.pixel2Int(f, c+1)); // Est
                          if(img[f+1][c+1] == BLACK) api.merge(this.pixel2Int(f, c), this.pixel2Int(f+1, c+1)); // SudEst
                          if(img[f+1][c] == BLACK) api.merge(this.pixel2Int(f, c),this.pixel2Int(f+1, c)); // Sud
                        }
                  }
                //  Thread.sleep(1);
               
              }
          }
          for(int f =  0; f< img.length - 1; f++){
              for(int c = 0; c < img[0].length - 1; c++){
                  if(f>0 && c>0){
                      int size = api.findCardinalitat(this.pixel2Int(f, c)); // Creo que el merge está fastidiado por esto todos deberían colgar del primero no?
                     if(size < llindar) img[f][c] = 255;
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
    
