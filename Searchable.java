package interfaces;

import exception.DonorNotFoundException;
import exception.InvalidBloodGroupException;
import model.Donor;

import java.util.ArrayList;

/**
 * ============================================================================
 * INTERFACE: Searchable
 * SYLLABUS TOPIC: Unit 1A - Abstraction and Interfaces
 *                 Unit 2  - ArrayList Return Collections
 * ============================================================================
 *
 * Defines standard search operations for locating registered blood donors
 * using different search criteria (ID, name, blood group, city).
 */
public interface Searchable
{
    Donor searchById(String donorId) throws DonorNotFoundException;

    ArrayList<Donor> searchByName(String nameKeyword);

    ArrayList<Donor> searchByBloodGroup(String bloodGroup) throws InvalidBloodGroupException;

    ArrayList<Donor> searchByCity(String city);
}
