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
	   final int BLACK=0;
      try {
          Class<?> mfclass = Class.forName("MFSet");
          MFSetInterface api = (MFSetInterface)mfclass.newInstance(); // deberías pasarle esto creo pixel2Int(img[0].length,img[1].length) que si no es null pointer.
          if (img == null) return;
          //COMPLETAR
          System.out.println("Empezamos!");
          for(int f = 0; f < img[0].length; f++) {
              double progress = (f/img[0].length)*100.0;
              System.out.println(progress + "% completado.");
              for (int c  = 0; c < img[1].length; c++) {
                  if(img[f][c]==BLACK){
                      System.out.println(api.find(img[f][c]));
                      if(img[f-1][c+1] == BLACK) api.merge(img[f][c], img[f-1][c+1]); // NordEst
                      if(img[f][c+1] == BLACK) api.merge(img[f][c], img[f][c+1]); // Est
                      if(img[f+1][c+1] == BLACK) api.merge(img[f][c], img[f+1][c+1]); // SudEst
                      if(img[f+1][c] == BLACK) api.merge(img[f][c], img[f+1][c]); // Sud
                      int size = api.findCardinalitat(img[f][c]); // Creo que el merge está fastidiado por esto todos deberían colgar del primero no?
                      if(size < llindar) img[f][c] = 255;
                  }
              }
          }
      } catch (ClassNotFoundException e) {
         System.out.println("algo mal");
         e.printStackTrace();
      } catch (IllegalAccessException e) {
          System.out.println("algo mal");
          e.printStackTrace();
      } catch (InstantiationException e) {
          System.out.println("algo mal");
          e.printStackTrace();
      } catch (Exception e) {
          System.out.println(e);
      }


     }


 public int [][] getImage(){
	  return img;
  }

}
