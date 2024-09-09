public class PriorQueue { // make this utilize aspects of the space class
    
    private Link<Space> first;

    public PriorQueue(){
        first = null;
    }

    public boolean isEmpty(){
        return first == null;
    }

    public void insert(Space data){
        if(isEmpty()){
            first = new Link<Space>(data, null, null);
        }
        else{
            Link<Space> current = first; // check the first one in the queue
            if(current.getData().getEntropy() > data.getEntropy()){
                first = new Link<Space>(data, null, current);
            }
            
            else{
                 while(current.getData().getEntropy() < data.getEntropy()){ // find a space in the queue where current is greater than the space to add
                    if(current.getNext() != null){
                    current = current.getNext(); 
                    }
                    else{
                        break;
                    }
                
                }
                current.updateNext(new Link<Space>(data, null, current.getNext())); // update the queue to next link
            }
        }
    }

    public Space remove(){
        if(isEmpty()){ // do not want null error
            return null;
        }
        Space toReturn = first.getData();
        first = first.getNext();
        return toReturn;
    }
}
