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
import com.chemaxon.descriptors.common.BinaryVectorDescriptor;
import com.chemaxon.descriptors.common.BinaryVectors;
import com.chemaxon.descriptors.common.DescriptorComparator;
import com.chemaxon.descriptors.common.DescriptorGenerator;
import com.chemaxon.descriptors.common.DescriptorParameters;
import com.chemaxon.descriptors.common.DescriptorSerializer;
import com.chemaxon.descriptors.common.Guarded;
import com.chemaxon.descriptors.common.binary.BinaryVectorDescriptorImpl;
import com.chemaxon.descriptors.common.binary.BinaryVectorDescriptorSerializer;
import com.chemaxon.descriptors.common.comparison.impl.BinaryLongComparisonContextFactory;
import com.google.common.base.Function;
import java.io.Serializable;

/**
 * Custom fingerprint generator.
 * 
 * This class represents the integration of the custom fingerprint ({@link MyFpCalculator}) into the Descriptors API
 * which makes this functionality available for MadFast.
 * 
 * Please note that this class uses non-public ChemAxon APIs. 
 * 
 * @author Gabor Imre
 */
public class MyFpGenerator implements DescriptorGenerator<BinaryVectorDescriptor>, Serializable {

    /**
     * The fingerprint calculation.
     */
    private final Function<Molecule, long[]> calc;

    /**
     * Length of the calculated fingerprint in bits.
     */
    private static final int LENGTH = MyFpCalculator.LENGTH_IN_BITS;
    
    
    private static final BinaryVectors.Endianness ENDIANNESS = BinaryVectors.Endianness.BIG_ENDIAN;
    private final Object guardObject;
    private final BinaryVectorDescriptorSerializer serializer;
    private final BinaryLongComparisonContextFactory comparison;
    
    /**
     * Construct.
     */
    public MyFpGenerator() {
        this.calc = new MyFpCalculator();
        this.guardObject = Guarded.New.guardObject();
        this.serializer = new BinaryVectorDescriptorSerializer(this.guardObject, LENGTH, ENDIANNESS);
        this.comparison = new BinaryLongComparisonContextFactory(LENGTH, this.serializer);
    }
    
    @Override
    public BinaryVectorDescriptor generateDescriptor(Molecule mlcl) {
        final long [] fp = this.calc.apply(mlcl);
        return new BinaryVectorDescriptorImpl(fp, this.guardObject, LENGTH, ENDIANNESS);

    }

    @Override
    public BinaryVectorDescriptor getBareDescriptor(BinaryVectorDescriptor b) {
        return b;
    }

    @Override
    public DescriptorParameters getParameters() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public BinaryLongComparisonContextFactory comparisonContextFactory() {
        return this.comparison;
    }

    @Override
    @Deprecated
    public DescriptorComparator<BinaryVectorDescriptor> getDefaultComparator() {
        return this.defaultComparison();
    }

    @Override
    public DescriptorComparator<BinaryVectorDescriptor> defaultComparison() {
        return this.comparison.defaultComparison().descriptorComparator();
    }

    @Override
    public byte[] toByteArray(BinaryVectorDescriptor b) {
        return this.serializer.toByteArray(b);
    }

    @Override
    public String toString(BinaryVectorDescriptor b) {
        return this.serializer.toString(b);
    }

    @Override
    public BinaryVectorDescriptor fromString(String string) {
        return this.serializer.fromString(string);
    }

    @Override
    public BinaryVectorDescriptor fromByteArray(byte[] bytes) {
        return this.serializer.fromByteArray(bytes);
    }

    @Override
    public boolean serializerIsEqualWith(DescriptorSerializer ds) {
        return this.serializer.serializerIsEqualWith(ds);
    }

    @Override
    public Object getGuardObject() {
        return this.guardObject;
    }

    @Override
    public String toString() {
        return "My custom fp generator";
    }
    
    
    
}
