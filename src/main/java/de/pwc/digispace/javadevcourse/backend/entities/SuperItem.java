package de.pwc.digispace.javadevcourse.backend.entities;

/**
 * 
 * @author john
 * Implements Item that can be ordered by customers
 * created: 30.01.2019
 *
 */
public class SuperItem{
		
	private final String superItemId;
	private final String description;
    
	public SuperItem( String superItemId, String description) {
		this.superItemId = superItemId;
		this.description = description;
	}

    /**
     * @return the superItemId
     */
    public String getSuperItemId() {
        return superItemId;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

}
