package frc.robot.subsystems;

import java.util.Arrays;
import io.github.pseudoresonance.pixy2api.links.Link;
import edu.wpi.first.wpilibj.I2C;
import io.github.pseudoresonance.pixy2api.Pixy2;
import io.github.pseudoresonance.pixy2api.Pixy2.Checksum;

/**
 * <p>Pixy2 Link class written in the same way as the I2CLink class, but allowing for mutliple port inputs.</p>
 * For more information about the file, see io.github.pseudoresonance.pixy2api.links.I2CLink.
 */
public class PortOptionI2CLink implements Link {

    private int targetAddress = 0x54;
	private final static int PIXY_I2C_MAX_SEND = 16; //don't send any more than 16 bytes at a time
	private I2C i2c = null;

	/**
	 * Creates an I2C link with the specified address.
	 * @param address I2C port
	 */
    public PortOptionI2CLink(int address) {
        targetAddress = address;
	}
	
	/**
	 * Opens I2C port.
	 * @param arg I2C port
	 * 
	 * @return Returns 0
	 */
	public int open(int arg) {
		I2C.Port port;
		switch (arg) {
		case 1:
			port = I2C.Port.kMXP;
			break;
		case Pixy2.PIXY_DEFAULT_ARGVAL:
		default:
			port = I2C.Port.kOnboard;
		}
		i2c = new I2C(port, targetAddress);
		return 0;
	}

	/**
	 * Closes I2C port.
	 */
	public void close() {
		i2c.close();
	}

	/**
	 * Receives and reads specified length of bytes from I2C.
	 *
	 * @param buffer Byte buffer to return value
	 * @param length Length of value to read
	 * @param cs Checksum
	 * 
	 * @return Length of value read
	 */
	public int receive(byte[] buffer, int length, Checksum cs) {
		int i, n;
		if (cs != null)
			cs.reset();
		for (i = 0; i < length; i += n) {
			//n is the number read -- it most likely won't be equal to length
			n = 0;
			byte[] read = new byte[length - i];
			i2c.readOnly(read, (length - i));
			for (int k = 0; k < read.length; k++) {
				n++;
				byte b = read[k];
				if (cs != null) {
					int csb = b & 0xff;
					cs.updateChecksum(csb);
				}
				buffer[k + i] = b;
			}
		}
		return length;
	}

	/**
	 * Receives and reads specified length of bytes from I2C.
	 *
	 * @param buffer Byte buffer to return value
	 * @param length Length of value to read
	 *
	 * @return Length of value read
	 */
	public int receive(byte[] buffer, int length) {
		return receive(buffer, length, null);
	}

	/**
	 * Writes and sends buffer over I2C.
	 *
	 * @param buffer Byte buffer to send
	 * @param length Length of value to send
	 *
	 * @return Length of value sent
	 */
	public int send(byte[] buffer, int length) {
		int i, packet;
		for (i = 0; i < length; i += PIXY_I2C_MAX_SEND) {
			if (length - i < PIXY_I2C_MAX_SEND)
				packet = (length - i);
			else
				packet = PIXY_I2C_MAX_SEND;
			byte[] send = Arrays.copyOfRange(buffer, i, packet);
			i2c.writeBulk(send, packet);
		}
		return length;
	}
}
