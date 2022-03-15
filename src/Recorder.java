import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class Recorder extends Thread{
    private static final int BITS = 16;
    private static final int HZ = 8000;
    //private static final int HZ = 44100;
    private static final int MONO = 1;
   
    // ���j�APCM 16bit 44100Hz x �P�b
    private byte[] voice = new byte[HZ * BITS / 8 * MONO];
    private TargetDataLine target;
    private AudioInputStream stream;

    public boolean g_bRecorder = false;
    // �R���X�g���N�^
    Recorder()
    {
        try
        {
            // �I�[�f�B�I�t�H�[�}�b�g�̎w��
            AudioFormat linear = new AudioFormat(HZ,BITS,MONO,true,false);
           
            // �^�[�Q�b�g�f�[�^���C�����擾
            DataLine.Info info = new DataLine.Info(TargetDataLine.class,linear);
            target = (TargetDataLine)AudioSystem.getLine(info);
            
            // �^�[�Q�b�g�f�[�^���C�����J��
            target.open(linear);
           
            // �}�C�N���͊J�n
            target.start();
           
            // ���̓X�g���[�����擾
            stream = new AudioInputStream(target);
           
        } catch (LineUnavailableException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        g_bRecorder = true;
    }
 // �X���b�h���s
    public void run()
    {
        while( true )
        {
            if( !g_bRecorder ) return;
            try
            {
                // �X�g���[�����特���f�[�^���擾
                stream.read( voice , 0, voice.length );
               
            } catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // �ꉞ�A�E�G�C�g
            try{
                Thread.sleep( 100 );
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    // �f�[�^�擾
    public byte[] getVoice()
    {
        return voice ;
    }
   
    // �I��
    public void end()
    {
        g_bRecorder = false;
       
        // �^�[�Q�b�g�f�[�^���C�����~
        target.stop();
       
        // �^�[�Q�b�g�f�[�^���C�������
        target.close();
    }
}
