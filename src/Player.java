import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Player extends Thread{
    private static final int BITS = 16;
    private static final int HZ = 8000;
	//private static final int HZ = 44100;
    private static final int MONO = 1;
   
    // ���j�APCM 16bit 44100Hz x �P�b
    private byte[] voice = new byte[ HZ * BITS / 8 * MONO ];
    private SourceDataLine source;

    public boolean g_bPlayer = false;

    // �R���X�g���N�^
    Player()
    {
        g_bPlayer = true;
       
        try
        {
            // �I�[�f�B�I�t�H�[�}�b�g�̎w��
            AudioFormat linear = new AudioFormat(HZ,BITS,MONO,true,false);
           
            // �\�[�X�f�[�^���C�����擾
            DataLine.Info info = new DataLine.Info(SourceDataLine.class,linear);
            source = (SourceDataLine)AudioSystem.getLine(info);
            // �\�[�X�f�[�^���C�����J��
            source.open( linear );
           
            // �X�s�[�J�[�o�͊J�n
            source.start();
           
        } catch (LineUnavailableException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
   
    // �X���b�h���s
    public void run()
    {
        while( true )
        {
            if( !g_bPlayer ) return;
           
            // �X�s�[�J�[�ɉ����f�[�^���o��
            source.write( voice, 0, voice.length );
           
            // �ꉞ�A�E�G�C�g
            try{
                Thread.sleep( 100 );
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
   
    // �f�[�^�ݒ�
    public void setVoice( byte[] b )
    {
        voice = b;
    }

    // �I��
    public void end()
    {
        g_bPlayer = false;
       
        // �\�[�X�f�[�^���C�����~
        source.stop();
       
        // �\�[�X�f�[�^���C�������
        source.close();
    }
}
