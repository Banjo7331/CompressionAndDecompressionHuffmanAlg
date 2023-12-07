package pl.edu.pw.ee.aisd2023zlab5;

import pl.edu.pw.ee.aisd2023zlab5.exceptions.ValidArgumentsException;

public class ApplicationHuffmanCompressionDecompression {
    public static void main(String []args){
        if (args.length != 3) {
            throw new ValidArgumentsException("Not correct arguments, need [Path of original file], [Path of directory to result file](if decompression file, because need to know extension), [true/false](depending if compress or decompress");
        }

        String sourceFilePath = args[0];
        String destinationFilePath = args[1];
        boolean compress = Boolean.parseBoolean(args[2]);
        Huffman h = new Huffman();
        if (compress) {
            h.start(sourceFilePath,destinationFilePath,compress);
        } else {
            h.start(sourceFilePath,destinationFilePath,compress);
        }
    }
}
