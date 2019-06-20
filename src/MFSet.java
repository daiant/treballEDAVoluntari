
public class MFSet implements MFSetInterface {
    int [] mfset;
    public MFSet() {}
    public MFSet(int numElem){
    mfset = new int[numElem];
    for (int i = 0; i < numElem; i++) {
        mfset[i]=-1;
    }
    }
    @Override
    public int find(int pos){
    if (pos < 0 || pos >= mfset.length){
        return -1; // no tÃ© representant
    }
    while (mfset[pos] > 0 ){
        pos = mfset[pos];
    }
    return pos;
    }
    @Override
    public boolean merge(int elem1, int elem2){
    //COMPLETAR
    int r1 = find(elem1);
    int r2 = find(elem2);
    if(r1 != r2) {
      if(mfset[r1]==mfset[r2]) {
        mfset[r1] = r2;
        mfset[r2]--;
      } else if(mfset[r1] < mfset[r2]) {
        mfset[r2] = r1;
      } else {
        mfset[r1] = r2;
      }
      return true; // Se ha mergeado
    }
    return false; // No hacia falta
    }
    @Override
    public int findCardinalitat(int x){
    //COMPLETAR
        int sizeX = find(x); // Número de pixeles en el conjunto, no?
        return Math.abs(sizeX); // Pues al revés
    }
}
