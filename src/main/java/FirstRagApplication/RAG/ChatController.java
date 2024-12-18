package FirstRagApplication.RAG;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);
    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder, VectorStore vectorStore){
        this.chatClient = builder
                .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore))
                .build();

        log.info("ChatClient initialized with vectorStore: {}", vectorStore);
    }

    @GetMapping("/")
    public String chat(){
        return chatClient.prompt()
                .user("wie lautet der Name des Artikels?") //an dieser Stelle kann eine Frage an das LLM eingegeben werden
                .call()
                .content();
    }
}
