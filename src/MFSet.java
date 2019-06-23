 package src;
public class MFSet {
    int [] mfset;

    public MFSet(int numElem){
    mfset = new int[numElem];
    for (int i = 0; i < numElem; i++) {
        mfset[i]=-1;
    }
    }
    public boolean initialize() {
        for(int i = 0; i < mfset.length; i++) {
            mfset[i]=-1;
        }return true;
    }
    public int find(int pos){
    if (pos < 0 || pos >= mfset.length){
        return -1; // no té representant
    }
    while (mfset[pos] > 0 ){
        pos = mfset[pos];
    }
    return pos;
    }

 public boolean merge(int elem1, int elem2){
        int rep1 = find(elem1);
    int rep2 = find(elem2);
    if (rep1 != rep2){
        if (mfset[rep1] <  mfset[rep2]) { //el arbre primer es mes gran
        mfset[rep2] = rep1;
        } else {
        mfset[rep1] = rep2;
        if (mfset[rep1] == mfset[rep2]){
            mfset[rep2]--;
        }
        }
        return true;
    }
    return false;
    }
    public int findCardinalitat(int x){
    //COMPLETAR
        int sizeX = find(x); // N�mero de pixeles en el conjunto, no?
        return Math.abs(sizeX); // Pues al rev�s
    }
}
