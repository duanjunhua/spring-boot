package com.duanjh.machine;

import edu.stanford.nlp.pipeline.*;
import okhttp3.*;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;

import java.util.Properties;


/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-05-14 周四 09:31
 * @Version: v1.0
 * @Description: 大预言模型
 */
public class LLMDemo {



    public static void main(String[] args) throws Exception {

        OkHttpClient client = new OkHttpClient();

//        simapleChat(client);

//        graphChat();

//        embed(client);

        berAndRe();
    }

    /**
     * 一、LLM：一种通过海量文本训练出来的、能理解和生成人类语言的巨型神经网络
     *  理解能力强，能处理各种自然语言任务；但会产生“幻觉”（编造事实），成本高，不掌握私有数据
     *
     *  用于：智能客服、内容生成、代码辅助、翻译
     */
    private static void simapleChat(OkHttpClient client) throws Exception {
        String json = "{\n" +
                " \"model\": \"gpt-3.5-turbo\",\n"+
                " \"messages\": [{\"role\": \"user\", \"content\": \"什么是大语言模型？\"}]\n"+
                "}";

        Request request = new Request.Builder()
                .url("https://api.bianxie.ai/v1/chat/completions")
                .header("Authorization", "Bearer sk-B1nbH6KWz0hWlxOZWK2eXCc5eNrFYs0I1QbuQ9UFNsBy2xKT")
                .post(RequestBody.create(json, MediaType.parse("application/json")))
                .build();

        Response response = client.newCall(request).execute();

        System.out.println(response.body().string());
    }

    /**
     * 二、RAG：让LLM在回答问题前，先去你的知识库里“查资料”，再根据资料回答
     *
     *  答案可溯源、知识实时更新、防止幻觉；但多了一步检索，延迟增加；检索质量决定答案质量
     *
     *  用于企业知识库问答、智能客服、法律条文查询
     */
    private static void ragChat(){

    }

    /**
     * 三、Agent：一个能自主规划任务、调用工具、执行操作的AI程序。
     *  普通LLM是“顾问”——你问它怎么办，它给你建议。Agent是“员工”——你给它一个目标，它会自己想办法完成，包括查资料、写代码、发邮件、甚至调用其他系统
     *
     *  能自主完成复杂任务，减少人工介入；但Token消耗大，可能陷入死循环，需要精心设计
     *
     *
     */
    private static void agentChat(){

    }

    /**
     * 四、知识图谱：用“实体-关系-实体”的三元组形式，把知识连接成一张网
     *  传统的知识库是一堆文档（像一堆散落的书），知识图谱是一张“关系地图”（像地铁线路图）
     *
     *  可使用Jena构建RDF知识图谱
     *  支持多跳推理，关系清晰，可解释性强；但构建成本高，需要领域专家参与
     *
     *  用于医疗诊断辅助、反欺诈（挖掘资金关系网）、智能推荐
     */
    private static void graphChat(){
        Model model = createGraphModel();

        graphQuery(model);
    }

    /**
     * 五、多模态（Multimodal）：同时处理文本、图像、音频、视频等多种类型的数据
     *
     *  更接近人类的感知方式，应用场景更广；但模型更大、计算成本更高、训练数据更难获取
     *
     *  用于自动驾驶（图像+雷达）、视频理解、图文生成、语音助手
     */
    private static void multiModel(){

        String prompt = "请描述给定图片的内容";
        String imageUrl = "";

        // 调用Open API（如：Deepseek、通义千问等）

        // 请求构造JSON，包含image和text


    }

    /**
     * 六、微调（Fine-tuning）：在预训练的大模型基础上，用少量特定领域的数据继续训练，让模型适应你的业务场景
     *
     *  微调通常需要Python环境，Java可通过HTTP调用微调服务，如：
     *  POST /v1/fine-tune
     *      {
     *          "model":"gpt-3.5-turbo",
     *          "training_data":"company_qa.jsonl",
     *          "epochs":3,
     *          "learning_rate":0.00002
     *      }
     *
     *  让模型更懂业务，回答更精准；但需要一定量的标注数据，训练成本较高（比RAG贵）
     *
     *  用于定制客服机器人、特定领域问答、代码生成模型适配公司代码风格
     */
    private static void fineTune(){

    }

    /**
     * 七、向量数据库与Embedding
     *
     *      Embedding（嵌入）：把文字、图片等转换成一组数字（向量），让计算机能计算“相似度”
     *      向量数据库：专门存储和检索向量的数据库，支持“给定一个向量，找出最相似的K个向量”
     *          常用向量数据库：Milvus、Qdrant、pgvector、Chroma
     *
     *  检索速度快（毫秒级），支持海量数据；但需要额外组件，不如传统数据库简单
     *
     *  用于RAG、推荐系统、图像相似度搜索
     */
    private static void embed(OkHttpClient client) throws Exception {

        // 待向量化文本
        String embedText = "SpringBoot AI 学习";

        // 定义使用的向量化模型
        String param = "{\"model\": \"nomic-embed-text\", \"input\":\""+embedText+"\"}";

        Request request = new Request.Builder()
                .url("http://localhost:11434/v1/embeddings")
                .post(RequestBody.create(param, MediaType.parse("json")))
                .build();

        Response response = client.newCall(request).execute();

        System.out.println(response.body().string());

    }

    /**
     * 八、工具调用（Tool）：让Agent能够调用外部函数或API，执行实际操作。
     *  可以查询数据库、发邮件、调用第三方API。比如，用户问“帮我订明天去上海的机票”，Agent会调用“查询航班”工具和“预订”工具
     *
     *  极大扩展Agent能力，让AI从“动口”变成“动手”；但需要安全控制，防止恶意调用
     *
     */
    private static void tollUse(){

    }

    /**
     * 九、ReAct模式：ReAct是Reason（推理）+ Act（行动）的缩写。Agent的“思考-行动-观察”循环，让AI能像人一样边想边做
     *
     *  让Agent能灵活应对变化，自动调整计划；但可能陷入无限循环，需要设置最大迭代次数
     *
     */
    private static void reActMode(){

    }

    /**
     * 十、实体识别（NER）与关系抽取（RE）
     *      实体识别（Named Entity Recognition, NER）：从文本中识别出人名、地名、组织名、时间等关键信息
     *      关系抽取（Relation Extraction, RE）：从文本中抽取出实体之间的关系
     */
    private static void berAndRe(){
        ner();
    }

    /**
     * 十一、文生图/文生视频：根据文字描述生成图片或视频
     *      代表模型：Stable Diffusion、DALL-E、Sora
     *
     *  极大降低视觉内容创作门槛；但可能生成不符合预期的内容，版权问题需注意
     *
     *  用于创意设计、游戏素材生成、广告制作
     */

    /**
     * 实体识别
     */
    private static void ner(){
        Properties props = new Properties();
        /**
         * tokenize：分词。将文本拆分为单词或符号级别的token
         * ssplit：句子分割。识别文本中的句子边界
         * pos：词性标注。为每个token标注词性（如名词、动词）
         * ner：命名实体识别。识别文本中的人名、地名、组织名等实体
         * parser：依存句法分析。分析句子中单词之间的语法依赖关系
         * coref：共指消解。识别文本中指向同一实体的不同表达（如代词指代）
         * sentiment：情感分析。判断文本的情感倾向（积极、消极、中性）
         */
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner");

        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        String document = "Michael will Study AI in NanChang";
        CoreDocument doc = new CoreDocument(document);
        pipeline.annotate(doc);


        /**
         * 输出：
         *  Michael ------> PERSON
         *  NanChang ------> CITY
         */
        for(CoreEntityMention em : doc.entityMentions()) {
            System.out.println(em.text() + " ------> " + em.entityType());
        }
    }

    /**
     * 关系抽取
     *  自动从非结构化文本中提取结构化知识；但准确率依赖模型，复杂关系难以抽取
     * 用于构建知识图谱、信息检索、智能问答
     *
     */
    private static void rextract(){

    }


    /**
     * 创建知识图谱
     */
    private static Model createGraphModel(){
        // 创建空模板
        Model model = ModelFactory.createDefaultModel();

        // 定义实体和关系：创建命名空间，简化URI的书写
        String ns = "http://duanjh.com/books/";
        Resource book = model.createResource(ns + "book1");
        Property title = model.createProperty(ns, "title");
        Property author = model.createProperty(ns, "author");

        Resource duanjh = model.createResource(ns + "duanjh");

        Literal bookTitle = model.createLiteral("Spring AI Note");

        // 添加三元组到模型中
        model.add(book, title, bookTitle);
        model.add(book, author, duanjh);

        // 查询
        StmtIterator stmtIterator = model.listStatements(book, title, (RDFNode) null);
        while (stmtIterator.hasNext()) {
            Statement stmt = stmtIterator.nextStatement();
            System.out.println(stmt.getObject());
        }

        return model;
    }

    /**
     * 执行SPARQL查：检索模型中的数据
     */
    private static void graphQuery(Model model){
        // 创建查询对象并执行SPARQL查询
        String queryString = "PREFIX ex: <http://duanjh.com/books/> " +
                "SELECT ?book ?title ?author WHERE { " +
                "?book ex:title ?title . " +
                "?book ex:author ?author . " +
                "}";
        Query query = QueryFactory.create(queryString);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect();
            ResultSetFormatter.out(System.out, results, query); // 打印查询结果
        } catch (Exception e) {
            e.printStackTrace(); // 处理异常情况
        }
    }

}
