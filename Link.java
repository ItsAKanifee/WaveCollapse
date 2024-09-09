public class Link<D> {
    
    private D data;
    private Link<D> nextLink; // points to the link in front
    private Link<D> prevLink; // points to the link behind

    public Link(D data){ // Constructor
        this.data = data;
        nextLink = null;
        prevLink = null;
    }

    public Link(D data, Link<D> prevLink, Link<D> nextLink){
        this.data = data;
        this.nextLink = nextLink;
        this.prevLink = prevLink;
    }

    public void updateNext(Link<D> next){ // Mutators
        nextLink = next;
    }

    public void updatePrev(Link<D> prev){
        prevLink = prev;
    }

    public Link<D> getNext(){ // Accessors
        return nextLink;
    }

    public Link<D> getLast(){
        return prevLink;
    }
    
    public D getData(){
        return data;
    }

}