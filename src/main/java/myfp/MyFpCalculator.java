/*
 * Copyright 2017 ChemAxon Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package myfp;

import chemaxon.struc.Molecule;
import com.chemaxon.descriptors.common.DescriptorGenerator;
import com.google.common.base.Function;
import java.io.Serializable;

/**
 * A trivial custom fingerprint calculator.
 * 
 * This class represents the <b>fingerprint</b> calculation which is the transformation of a chemical structure into a
 * bit string. To use this fingerprint in MadFast (or from the jklustor codes) it must be delegated by a 
 * {@link DescriptorGenerator} which supplies additional functionalities (serialization, parsing, comparisons). The 
 * wrapping is provided by {@link MyFpGenerator}.
 * 
 * A binary fingerprint is calculated for a {@link Molecule} which has the first {@link Molecule#getAtomCount()} bits
 * set.
 * 
 * @author Gabor Imre
 */

public class MyFpCalculator implements Function<Molecule, long []>, Serializable {
    
    /**
     * Number of bits in the fingerprint.
     * 
     * Must be a multiply of {@link Long#SIZE} (64 bits).
     */
    public static final int LENGTH_IN_BITS = 128;
    
    /**
     * Number of {@code long}s in the packed {@code long[]} array representation.
     */
    public static final int LENGTH_IN_LONGS = LENGTH_IN_BITS / Long.SIZE;

    @Override
    public long[] apply(Molecule m) {
        // Cap the number of set bits with the bit count.
        final int totalBitsToSet = m.getAtomCount() > LENGTH_IN_BITS ? LENGTH_IN_BITS : m.getAtomCount();
        
        // Packed array to be filled.
        final long [] ret = new long[LENGTH_IN_LONGS];
        
        for (int i = 0; i < LENGTH_IN_LONGS; i++) {
            
            // Number of bits to be set; at most i * 64 bits are set already
            final int remainingBits = Math.max(totalBitsToSet - i * Long.SIZE, 0);
            
            // Cap the value with 64 bits
            final int bitsToSet = Math.min(remainingBits, Long.SIZE);
            
            // take a long value having all bits set and shift out bits
            ret[i] = ~0l << (Long.SIZE - bitsToSet);
        }
        
        return ret;
    }
    
}
