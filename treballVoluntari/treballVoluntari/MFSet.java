package treballVoluntari;

public class MFSet {
    int [] mfset;

    public MFSet(int numElem){
    mfset = new int[numElem];
    for (int i = 0; i < numElem; i++) {
        mfset[i]=-1;
    }
    }

    public int find(int pos){
    if (pos < 0 || pos >= mfset.length){
        return -1; // no tÃ© representant
    }
    while (mfset[pos] > 0 ){
        pos = mfset[pos];
    }
    return pos; 
    }

    public boolean merge(int elem1, int elem2){
          int rep1 = find(elem1);
	int rep2 = find(elem2);

	//if (rep1 <0 || rep2 <0) return false;

	if (rep1 != rep2){
	    if (mfset[rep1] <  mfset[rep2]) { //el arbre primer es mes gran
		mfset[rep2] = rep1;
		mfset[rep1] += -1;
	    } else {
		mfset[rep1] = rep2;
		mfset[rep2] += -1;
	    }
	}
	return true;
    }
    
    public int findCardinalitat(int x){
        // int res = 1;
        // int num = find(x);
        // for(int i = 0; i < mfset.length -1; i++){
            // if(mfset[i] == num){
                // res += findCardinalitat(x);
            // }
        // }
        // return res;
        int sizeX = find(x); // Número de pixeles en el conjunto, no?
        return Math.abs(mfset[sizeX]); // Pues al revés
    }
}
