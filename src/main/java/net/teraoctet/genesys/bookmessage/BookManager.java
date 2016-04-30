package net.teraoctet.genesys.bookmessage;

import com.google.common.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

public class BookManager {
    public static File file = new File("config/genesys/book.conf");
    public static final ConfigurationLoader<?> manager = HoconConfigurationLoader.builder().setFile(file).build();
    public static ConfigurationNode book = manager.createEmptyNode(ConfigurationOptions.defaults());
    
    public BookManager(){}
    
    public static void init() {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            book = manager.load();
        } catch (IOException e) {}	
    }
    
    private Text getNodeText(String title, String node){
        try {
            List<String> list = new ArrayList<>();
            list = book.getNode(title,node).getList(TypeToken.of(String.class));
            Text msg = MESSAGE("");
            
            for(String s : list) {
                if (s.contains("#")){
                    msg = Text.builder()
                            .append(msg)
                            .append(textWithActions(s))
                            .build();
                }else{
                    msg = Text.builder()
                            .append(msg)
                            .append(MESSAGE(s + "\n"))
                            .build();
                }
            }
            return msg;           
        }catch(ObjectMappingException ex){
            return null;
        } 
    }    
    
    @SuppressWarnings("null")
    private Text textWithActions(String s){
        String arg[] = s.split("#");
        Text textPage = MESSAGE("");
        Player p;
        int i = 0;
        for(String chaine : arg){
            switch(chaine){
                case "onclick":
                    textPage = Text.builder()
                            .append(textPage)
                            .append(MESSAGE(arg[i+1]))
                            .onClick(TextActions.runCommand(arg[i+2]))
                            .build();
                    i++;
                    break;
                case "onhover":
                    textPage = Text.builder()
                            .append(textPage)
                            .append(MESSAGE(arg[i+1]))
                            .onHover(TextActions.showText(MESSAGE(arg[i+2])))
                            .build();
                    i++;
                    break;
                default:
                    textPage = Text.builder()
                            .append(textPage)
                            .append(MESSAGE(arg[i]))
                            .build();
                    break;
            }
            i++;
            if(i+1 >= arg.length){break;}
        }
        textPage = Text.builder()
                .append(textPage)
                .append(MESSAGE("\n"))
                .build();
        return textPage;
    }
    
    /**
     * Retourne un objet BookView
     * @param title titre du livre a retourner
     * @return BookView
     */    
    public BookView getBookMessage(String title){ 
        Text author = getNodeText(title,"author");
        BookView.Builder bookMessage = BookView.builder()
                .author(author)
                .title(Text.of(TextColors.GOLD, title));
        int x = 1;
        while(!getNodeText(title, String.valueOf(x)).toPlain().equalsIgnoreCase("")){
            bookMessage.addPage(getNodeText(title,String.valueOf(x)));
            x++;
        }
        return bookMessage.build();
    }
}
