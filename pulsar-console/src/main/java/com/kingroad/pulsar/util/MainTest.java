package com.kingroad.pulsar.util;


import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingroad.pulsar.domain.vo.prometheus.PrometheusItem;
import lombok.extern.slf4j.Slf4j;
import org.apache.bookkeeper.client.BKException;
import org.apache.bookkeeper.client.BookKeeperAdmin;
import org.apache.bookkeeper.client.LedgerHandle;
import org.apache.bookkeeper.client.api.LedgerMetadata;
import org.apache.bookkeeper.conf.ClientConfiguration;
import org.apache.bookkeeper.net.BookieId;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.pulsar.client.admin.*;
import org.apache.pulsar.client.impl.auth.AuthenticationToken;
import org.apache.pulsar.common.policies.data.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author: Michael J H Duan[JunHua]
 * @Date: 2026-07-29 周三 15:39
 * @Version: v1.0
 * @Description:
 */
@Slf4j
public class MainTest {

    // Pulsar超级管理员Token
    public static final String ADMIN_USER_TOKEN = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJicm9rZXItYWRtaW4ifQ.PFGexn4KKaw24udRzS1U2bsdL2WL64sOVapKV55B2cdO5QzhmpHELvtT3x4wF76WcewYym-GLhEqSMSl53oN9W11Z_B4LjcJ-bvYvPtkjoWP8lrshtsF5CiOlkdxWsYW1BMF4DCb681UZnEpxIW8vvx84E6wXKGDcm2LO-eKR9HYOKaTBe8mC-4SSYBd5jukdVDs_o5F5TzAwuLBxs7ltzr3DX9nDXN7AHtxcuE9Vn8R0bX4snkQXy-laR5j9bVYzPyjlplMdPZrda1MNBJlj7oGJpUSDe2-Fglkdv1L8NkGIOksfRWZAMwO-NReDCsA_PSwKK-af2HZikjm1dl3VQ";
    // Pulsar 生产消费Token
    public static final String CON_PRO_TOKEN = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJURVNUNjY2X2RmODJmYWQyIiwiaWF0IjoxNzg1NzIzMDg4LCJleHAiOjE3ODYzMjc4ODh9.iqRXaSFp-IfiwfxOGLi-s3Xb9cTCa7lh5u6GcM-yEAE24ya2I0BENrRfJR7qSkIuD5iTQO3peZhshbg-xLWnEe381pZ1KqDpsjJI8sDH_tO2FuK9LFEork6d5rA-DITsAJC47G4zLUHsjtIvj0KAQxvNuJHSAjuOManLszUc6lkxYTX6HvztiZOlzFoHgqCqFhyf18C6eVHMBdR1JevSlw4g_gAwVjDqK9U1Hcd4Uqfk09LOz4W_ij5ATO6qpIVtpDActJGqdNLTIyVDPyg8EbqF--voxUf1NtnwtZJNf_A4tPcaEQi618mS-EYfLiSQlpMsnb9c4dihaTuc3tNShg";

    public static final ObjectMapper mapper = new ObjectMapper();

    // ZK集群地址
    public static final String ZK_CONNECT_STR = "36.200.40.90:2181";
    //Pulsar默认BK账本ZK根路径
    private static final String BK_ZK_ROOT = "/ledgers";
    // Bookie默认metrics端口
    private static final int BOOKIE_METRICS_PORT = 8000;

    public static void main(String[] args) throws Exception {

        String defaultCluster = "st-util-cluster", defaultTenant = "zevent", defaultNamespace = "display-system", defaultTopic = "road-msg";

        JSONObject object  = new JSONObject();
        object.put("token", ADMIN_USER_TOKEN);

        PulsarAdmin admin = PulsarAdmin.builder()
                .serviceHttpUrl("http://36.200.40.93:8080")
                .connectionTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .requestTimeout(20, TimeUnit.SECONDS)
                .authentication(
                        AuthenticationToken.class.getName(),
                        JSONUtil.toJsonStr(object)
                )
                .build();

        // 集群管理
        clusterOperation(admin, defaultCluster, true);

        // 租户管理
//        tenantOperation(admin, defaultCluster, defaultTenant, true);

        // 命名空间管理
//        namespaceOperation(admin, defaultCluster, defaultTenant, defaultNamespace, false);


        System.exit(0);
    }

    /**
     * 集群管理
     */
    public static void clusterOperation(PulsarAdmin admin, String defaultCluster, boolean isTest) throws Exception {
        Clusters clusterManagement = admin.clusters();

        // 查看集群
        List<String> clusters = clusterManagement.getClusters();
        log.info("----------- 1.集群列表 -----------");
        log.info(" {}对应的集群列表：{} \n", admin.getServiceUrl(), JSONUtil.toJsonStr(clusters));

        // 获取集群信息
        log.info("----------- 2.集群详细信息 -----------");
        if(CollectionUtils.isNotEmpty(clusters)) {
            clusters.forEach(cls -> {
                try {
                    ClusterData cluster = clusterManagement.getCluster(cls);
                    log.info("{}集群对应的集群信息：{}", cls, JSONUtil.toJsonStr(cluster));
                } catch (PulsarAdminException e) {
                    throw new RuntimeException(e);
                }
            });
            System.out.println();
        }

        log.info("----------- 3.创建集群 -----------");
        ClusterData clusterData = ClusterData.builder()
                .serviceUrl("http://36.200.40.94:8080")
                // Broker 之间互通启用 TLS时配置
                // .serviceUrlTls("http://36.200.40.94:8080")

                .brokerServiceUrl("pulsar://36.200.40.94:6550")
                // .brokerServiceUrlTls("pulsar+ssl://36.200.40.94:6550")

                // 开启broker客户端TLS（broker访问远端集群使用）
                // .brokerClientTlsEnabled(true)
                // .tlsAllowInsecureConnection(false)

                // PEM证书方式
                // .brokerClientTrustCertsFilePath("/pulsar/certs/ca.crt")
                // .brokerClientCertificateFilePath("/pulsar/certs/client.crt")
                // .brokerClientKeyFilePath("/pulsar/certs/client.key")

                // 设置对等集群（开启双向复制需要两边适配），可选，如：ut-cluster-master
                // .peerClusterNames(new LinkedHashSet<>(List.of("ut-cluster-master")))

                .build();

        // 创建自定义集群
        if(!clusters.contains(defaultCluster)) {
            clusterManagement.createCluster(defaultCluster, clusterData);
        }

        // 查询创建的集群
        ClusterData newClusterData = clusterManagement.getCluster(defaultCluster);
        log.info("新集群【{}】创建成功，集群信息：{} \n", defaultCluster, JSONUtil.toJsonStr(newClusterData));

        log.info("----------- 4. 查看集群版本信息 -----------");
        Brokers brokers = admin.brokers();
        if(ObjectUtils.isEmpty(brokers)) {
            return;
        }
        String version = brokers.getVersion();
        log.info("集群版本:{}",  version);

        brokers.getActiveBrokers().forEach(broker -> {
            try {
                Map<String, String> runtimeConfigurations = brokers.getRuntimeConfigurations();
                log.info("Broker【{}】运行配置: {}", broker, JSONUtil.toJsonStr(runtimeConfigurations));
            } catch (PulsarAdminException e) {
                throw new RuntimeException(e);
            }
        });

        // 3. 获取业务运行统计指标：消息、连接、Topic业务统计
        BrokerStats stats = admin.brokerStats();

        String metrics = stats.getMetrics();

        List<PrometheusItem> items = mapper.readValue(metrics, new TypeReference<List<PrometheusItem>>() {});

        // 获取连接指标 brk_active_connections
        Optional<PrometheusItem> connMetricOpt = items.stream()
                .filter(item -> {
                    Map<String, String> dim = item.getDimensions();
                    return "broker_connection".equals(dim.get("metric"))
                            && "36.200.40.93".equals(dim.get("broker"));
                })
                .findFirst();

        connMetricOpt.ifPresent(item -> {
            Object activeConn = item.getMetrics().get("brk_active_connections");
            System.out.println("活跃连接 brk_active_connections = " + activeConn);
        });

        // 获取loadBalancing下CPU使用率 brk_lb_cpu_usage
        items.stream()
                .filter(item -> "loadBalancing".equals(item.getDimensions().get("metric")))
                .findFirst()
                .ifPresent(item -> {
                    Object cpuUsage = item.getMetrics().get("brk_lb_cpu_usage");
                    System.out.println("CPU使用率 brk_lb_cpu_usage = " + cpuUsage);
                });


        // 测试不删除测试集群
        if(isTest) {
            return;
        }
        log.info("----------- 99. 删除集群 -----------");

        // 删除集群
        clusterManagement.deleteCluster(defaultCluster);
        clusters = clusterManagement.getClusters();
        log.info("集群【{}】删除{} \n",  defaultCluster, clusters.contains(defaultCluster) ? "失败！" : "成功!");
    }

    /**
     * 集群下的租户管理
     */
    public static void tenantOperation(PulsarAdmin admin, String defaultCluster, String defaultTenant, boolean isTest) throws PulsarAdminException {
        Tenants tenantManager = admin.tenants();

        // 获取租户列表
        List<String> list = tenantManager.getTenants();
        log.info("----------- 1.租户列表 -----------");
        log.info("租户列表：{} \n", JSONUtil.toJsonStr(list));

        log.info("----------- 2.租户详情 -----------");
        if(CollectionUtils.isNotEmpty(list)) {
            list.forEach(tenant -> {
                try {
                    TenantInfo tenantInfo = tenantManager.getTenantInfo(tenant);
                    log.info("{}租户详细信息：{}", tenant, JSONUtil.toJsonStr(tenantInfo));
                } catch (PulsarAdminException e) {
                    throw new RuntimeException(e);
                }
            });
            System.out.println();
        }

        log.info("----------- 3.创建租户 -----------");
        TenantInfo tenantInfo = TenantInfo.builder()
                .adminRoles(new HashSet<>(List.of("ROLE_ADMIN")))
                .allowedClusters(new HashSet<>(List.of(defaultCluster)))
                .build();
        // 创建新租户
        if(!list.contains(defaultTenant)) {
            tenantManager.createTenant(defaultTenant, tenantInfo);
        }

        // 查询创建的集群
        TenantInfo info = tenantManager.getTenantInfo(defaultTenant);
        log.info("新租户【{}】创建成功，租户信息：{} \n", defaultTenant, JSONUtil.toJsonStr(info));

        // 测试不删除测试租户
        if(isTest) {
            return;
        }

        log.info("----------- 4. 删除租户 -----------");
        // 先删除其下关联的namespace、topic
        Namespaces namespaceApi = admin.namespaces();
        List<String> spaces = namespaceApi.getNamespaces(defaultTenant);
        if(CollectionUtils.isNotEmpty(spaces)) {
            spaces.forEach(space -> {
                try {
                    namespaceApi.deleteNamespace(space);
                } catch (PulsarAdminException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        tenantManager.deleteTenant(defaultTenant);
        list = tenantManager.getTenants();
        log.info("租户【{}】删除{} \n",  defaultTenant, list.contains(defaultTenant) ? "失败！" : "成功!");
    }



    /**
     * 命名空间管理
     */
    public static void namespaceOperation(PulsarAdmin admin, String newCluster, String tenant, String defaultNamespace, boolean isTest) throws Exception {
        Namespaces namespaceManager = admin.namespaces();

        List<String> namespaces = namespaceManager.getNamespaces(tenant);

        log.info("{}租户下的命名空间列表：{}",tenant, JSONUtil.toJsonStr(namespaces));

        log.info("----------- 2.命名空间详情 -----------");
        if(CollectionUtils.isNotEmpty(namespaces)) {
            namespaces.forEach(namespace -> {
                try {

                    List<String> replicationClusters = namespaceManager.getNamespaceReplicationClusters(namespace);
                    log.info("{}命名空间绑定集群（复制集群/联邦集群场景）配置信息：{}", namespace, JSONUtil.toJsonStr(replicationClusters));

                    /**
                     * 消息保留策略：消息ack后，还会在broker保存多久/多大容量
                     *  保留时长(分钟)：policies.getRetentionTimeInMinutes()，-1表示无限保留
                     *  保留容量(MB)：policies.getRetentionSizeInMB()，-1表示不限制保留容量
                     */
                    RetentionPolicies retentionPolicies = namespaceManager.getRetention(namespace);
                    log.info("{}命名空间消息保留策略【RetentionPolicies】配置信息：{}", namespace, JSONUtil.toJsonStr(retentionPolicies));

                    /**
                     * 持久化策略：副本数、ack策略、写入ledger策略
                     *  副本数量：persistencePolicies.getBookkeeperEnsemble()
                     *  写入成功确认副本数：persistencePolicies.getBookkeeperWriteQuorum()
                     *  ACk要求副本数：persistencePolicies.getBookkeeperAckQuorum()
                     *  副本要求最大标记删除率：persistencePolicies.getManagedLedgerMaxMarkDeleteRate()
                     */
                    PersistencePolicies persistencePolicies = namespaceManager.getPersistence(namespace);
                    log.info("{}命名空间持久化策略【PersistencePolicy】配置信息：{}", namespace, JSONUtil.toJsonStr(persistencePolicies));

                    Integer maxProducersPerTopic = namespaceManager.getNamespaceMessageTTL(namespace);
                    log.info("{}命名空间消息TTL（未消费消息自动过期时间）配置信息：{}", namespace, JSONUtil.toJsonStr(maxProducersPerTopic));

                    // BundlesData用于存储和监控Namespace Bundle‌（命名空间分片）的实时负载统计信息
                    BundlesData bundles = namespaceManager.getBundles(namespace);
                    log.info("{}命名空间分片信息：{} \n", namespace, JSONUtil.toJsonStr(bundles));
                } catch (PulsarAdminException e) {
                    throw new RuntimeException(e);
                }
            });
            System.out.println();
        }

        log.info("----------- 3.创建命名空间 -----------");
        String ts = tenant + "/" + defaultNamespace;    // 注意：命名空间必须时租户与空间名的组合
        if(CollectionUtils.isEmpty(namespaces) || !namespaces.contains(ts)) {
            namespaceManager.createNamespace(ts, new HashSet<>(List.of(newCluster)));
        }
        namespaces = namespaceManager.getNamespaces(tenant);
        if(CollectionUtils.isNotEmpty(namespaces) && namespaces.contains(ts)) {
            log.info("新命名空间【{}】创建成功 \n", ts);
        }

        log.info("----------- 4. 命名空间配置 -----------");

        // 设置集群
        namespaceManager.setNamespaceAllowedClusters(ts, new HashSet<>(List.of(newCluster)));
        // 需要先设置授权setNamespaceAllowedClusters集群
        namespaceManager.setNamespaceReplicationClusters(ts, new HashSet<>(List.of(newCluster)));
        // 消息保留策略配置
        if(ObjectUtils.isEmpty(namespaceManager.getRetention(ts))) {
            RetentionPolicies retentionPolicies = new RetentionPolicies(-1, -1);
            namespaceManager.setRetention(ts, retentionPolicies);
        }

        // 设置命名空间下所有topic消息存留时间（秒），如设置一天，默认不限制
        // namespaceManager.setNamespaceMessageTTL(ts, 60*60*24);

        // 持久化策略
        if(ObjectUtils.isEmpty(namespaceManager.getPersistence(ts))) {
            PersistencePolicies persistencePolicies = new PersistencePolicies(3, 3, 3, 0);
            namespaceManager.setPersistence(ts, persistencePolicies);
        }

        log.info("----------- 5. 删除命名空间 -----------");
        if(isTest) {
            return;
        }

        // 先删除其下所有TOPIC
        List<String> topics = namespaceManager.getTopics(ts);
        if(CollectionUtils.isNotEmpty(topics)) {
            Topics topicApi = admin.topics();
            topics.forEach(topic -> {
                try {
                    topicApi.delete(topic);
                } catch (PulsarAdminException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        namespaceManager.deleteNamespace(ts);
        log.info("租户{}下的命名空间【{}】已删除{} \n",  tenant, defaultNamespace, CollectionUtils.isNotEmpty(namespaces) && namespaces.contains(ts)? "失败！" : "成功!");
    }


    /**
     * 集群版本信息、节点数量、集群运行时间、实时监控集群整体运行状态，包括 Broker、BookKeeper、ZooKeeper 节点数量、
     * 运行状态及健康状态、Broker 节点信息，包括节点地址、运行状态、负载情况及连接数量，配置Broker各项参数
     * @param admin
     */
    public static void getBrokerMetrics(PulsarAdmin admin) throws Exception {
        List<String> clusters = admin.clusters().getClusters();
        log.info("集群列表：{}", JSONUtil.toJsonStr(clusters));


        //查看集群在线运行状态、集群接入时间及关联节点资源概况。
        if(CollectionUtils.isEmpty(clusters))  {
            System.exit(0);
            return;
        }

        for(String cluster : clusters) {
            if(!cluster.contains("ZEventCenter")) continue;
            BrokerStats stats = admin.brokerStats();
            JSONArray metrics = JSONUtil.parseArray(stats.getMetrics());
            log.info("集群：{}，对应的指标：{}", cluster, JSONUtil.toJsonStr(metrics));
        }

        if(clusters.contains("ZEventCenter")){

            // 获取集群所有在线Broker节点地址集合
            List<String> activeBrokers = admin.brokers().getActiveBrokers("ZEventCenter");
            System.out.println(activeBrokers);

            // 集群整体状态判定
            String clusterStatus = CollectionUtils.isNotEmpty(activeBrokers) ? "运行中" : "离线不可用";
            // 在线节点数量
            int onlineBrokerNum = activeBrokers.size();
            log.info("集群状态：{}， 在线Broker数量：{}", clusterStatus, onlineBrokerNum);

            // 获取集群Leader主Broker
            BrokerInfo leaderBroker = admin.brokers().getLeaderBroker();
            log.info("集群Leader主Broker：{}", JSONUtil.toJsonStr(leaderBroker));

            // 集群就绪健康校验
            BrokerStats brokerStats = admin.brokerStats();
            log.info("集群健康状态：{}", JSONUtil.toJsonStr(brokerStats));

            // 集群接入访问地址信息
            ClusterData clusterData = admin.clusters().getCluster("ZEventCenter");
            log.info("集群{}接入访问地址信息：{}", "ZEventCenter", JSONUtil.toJsonStr(clusterData));

            // 跨集群复制对等集群列表
            Set<String> peerClusters = admin.clusters().getPeerClusterNames("ZEventCenter");
            log.info("跨集群复制对等集群列表：{}", JSONUtil.toJsonStr(peerClusters));
        }
    }

    /**
     * 查看 BookKeeper 存储节点信息，包括节点状态、Ledger 存储情况及磁盘使用率，配置Bookeeper各项参数，如副本等。
     * @param admin
     */
    public static void getBookKeeperMetrics(PulsarAdmin admin) throws Exception {
        ClientConfiguration conf = new ClientConfiguration();
        conf.setZkServers(ZK_CONNECT_STR);
        conf.setZkLedgersRootPath(BK_ZK_ROOT);

        // 创建BookKeeper Admin
        try (BookKeeperAdmin bkAdmin = BookKeeperAdmin.newBookKeeperAdmin(conf)) {
            // 1. 获取所有在线Bookie节点
            Collection<BookieId> onlineBookies = bkAdmin.getAvailableBookies();
            log.info("========== 在线BookKeeper Bookie节点列表 ==========");
            for (BookieId bookieId : onlineBookies) {

            }


            Collection<BookieId> readOnlyBookies = bkAdmin.getReadOnlyBookies();
            log.info("========== 只读BookKeeper Bookie节点列表 ==========");

            for (BookieId bookieId : onlineBookies) {
                log.info("Bookie节点：{}", bookieId);

                // 查询Bookie信息（是否只读、可用状态）
                boolean isReadOnly = CollectionUtils.isNotEmpty(readOnlyBookies) && readOnlyBookies.contains(bookieId);
                log.info("是否只读模式：{}", isReadOnly);

                // 拉取该bookie磁盘使用率指标
                String diskMetrics = fetchBookieDiskMetrics(bookieId.getId().split(":")[0], BOOKIE_METRICS_PORT);
                System.out.println("    磁盘指标：" + diskMetrics);
            }

            // 2. 获取所有Ledger ID（元数据遍历）
            log.info("\n========== 遍历Ledger账本信息 ==========");
            Iterable<Long> ledgerIds = bkAdmin.listLedgers();
            if(ObjectUtils.isEmpty(ledgerIds))  return;
            for(Long ledgerId : ledgerIds){
                queryLedgerInfo(bkAdmin, ledgerId);
            }

            // 3. 获取故障Bookie / 离线节点（ZK元数据）
            Collection<BookieId> offlineBookies = bkAdmin.getAllBookies().stream().filter(bId -> !onlineBookies.contains(bId)).collect(Collectors.toList());
            log.info("\n========== 离线Bookie节点 ==========");
            offlineBookies.forEach(System.out::println);



        } catch (Exception e) {
            log.error("获取BooKKeeper指标失败：{} - {}", e.getLocalizedMessage(), e.getCause());
        }
    }

    /**
     * 查询单个Ledger详细信息：副本分布、E/W/Q配置
     */
    public static void queryLedgerInfo(BookKeeperAdmin bkAdmin, long ledgerId) throws BKException, InterruptedException {

        LedgerHandle ledgerHandle = bkAdmin.openLedger(ledgerId);

        LedgerMetadata metadata = bkAdmin.getLedgerMetadata(ledgerHandle);
        log.info("LedgerId = {}", ledgerId);
        log.info("副本节点总数EnsembleSize(E) = {}", metadata.getEnsembleSize());
        log.info("写入副本数WriteQuorum(W) = {}", metadata.getWriteQuorumSize());
        log.info("确认副本数AckQuorum(Q) = {}", metadata.getAckQuorumSize());
        log.info("账本状态：{}", (metadata.isClosed() ? "已关闭" : "开放中"));

        // 输出Ledger各个片段对应的Bookie副本节点分布
        NavigableMap<Long, ? extends List<BookieId>> ensembleMap = metadata.getAllEnsembles();
        for (Long entryId : ensembleMap.keySet()) {
            List<BookieId> replicas = ensembleMap.get(entryId);
            System.out.printf("分片起始Entry=%d 副本节点列表: %s%n", entryId, replicas);
        }
    }


    /**
     * 查看 ZooKeeper 节点信息，包括节点角色、运行状态及集群健康状态
     */
    public static void getZookeeperMetrics(PulsarAdmin admin) throws Exception {

    }

    /**
     * HTTP请求Bookie Prometheus接口，解析磁盘空闲/总容量
     * ⚠️BookKeeper原生SDK不提供磁盘使用率API，生产标准方案
     */
    public static String fetchBookieDiskMetrics(String host, int metricsPort) throws IOException {
        String url = String.format("http://%s:%d/metrics", host, metricsPort);

        HttpRequest request1 = HttpUtil.createRequest(Method.GET, url);
        HttpResponse resp = request1.execute();
        String responseBody = resp.body().toString();

        log.info("{}对应的指标如下：{}", url, JSONUtil.toJsonStr(responseBody));

        // 提取核心指标：bookie_disk_total_bytes / bookie_disk_free_bytes
        String total = extractMetricValue(responseBody, "bookie_disk_total_bytes");
        String free = extractMetricValue(responseBody, "bookie_disk_free_bytes");
        if (total == null || free == null) {
            return "指标未采集";
        }
        double totalGb = Long.parseLong(total) / 1024.0 / 1024 / 1024;
        double freeGb = Long.parseLong(free) / 1024.0 / 1024 / 1024;
        double usedRate = (1 - Long.parseLong(free) * 1.0 / Long.parseLong(total)) * 100;
        return String.format("总容量=%.2fGB,空闲=%.2fGB,使用率=%.2f%%", totalGb, freeGb, usedRate);
    }

    /**
     * 简单解析prometheus文本指标
     */
    private static String extractMetricValue(String text, String metricName) {
        String[] lines = text.split("\n");
        for (String line : lines) {
            if (line.startsWith(metricName) && !line.startsWith("#")) {
                String[] parts = line.split(" ");
                if (parts.length >= 2) {
                    return parts[1];
                }
            }
        }
        return null;
    }
}
