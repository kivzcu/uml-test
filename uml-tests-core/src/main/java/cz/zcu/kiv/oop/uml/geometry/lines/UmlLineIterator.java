package cz.zcu.kiv.oop.uml.geometry.lines;

import java.util.Iterator;

import cz.zcu.kiv.oop.uml.geometry.UmlLine;

/**
 * Iterator of parts of line shape (if the line shape is composite of more lines).
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public interface UmlLineIterator extends Iterator<UmlLine>, Iterable<UmlLine> {

}
