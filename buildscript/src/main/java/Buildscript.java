import io.github.coolcrabs.brachyura.decompiler.BrachyuraDecompiler;
import io.github.coolcrabs.brachyura.decompiler.fernflower.FernflowerDecompiler;
import io.github.coolcrabs.brachyura.fabric.*;
import io.github.coolcrabs.brachyura.fabric.FabricContext.ModDependencyCollector;
import io.github.coolcrabs.brachyura.fabric.FabricContext.ModDependencyFlag;
import io.github.coolcrabs.brachyura.maven.Maven;
import io.github.coolcrabs.brachyura.maven.MavenId;
import io.github.coolcrabs.brachyura.minecraft.Minecraft;
import io.github.coolcrabs.brachyura.minecraft.VersionMeta;
import io.github.coolcrabs.brachyura.quilt.QuiltMaven;
import io.github.coolcrabs.brachyura.processing.ProcessorChain;
import net.fabricmc.mappingio.tree.MappingTree;

public class Buildscript extends SimpleFabricProject {

	@Override
	public String getModId() {
		return Properties.MOD_ID;
	}

	@Override
	public String getVersion() {
		return Properties.MOD_VERSION;
	}

	@Override
	public VersionMeta createMcVersion() {
		return Minecraft.getVersion(Properties.MINECRAFT);
	}

	@Override
	public MappingTree createMappings() {
		return Yarn.ofMaven(FabricMaven.URL, FabricMaven.yarn(Properties.YARN_MAPPINGS)).tree;
	}

	@Override
	public FabricLoader getLoader() {
		return new FabricLoader(FabricMaven.URL, FabricMaven.loader(Properties.FABRIC_LOADER));
	}

	@Override
	public void getModDependencies(ModDependencyCollector d) {
		d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", "fabric-api", Properties.FABRIC_API), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
		d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", "fabric-api-base", "0.4.11+e62f51a390"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
		d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", "fabric-api-lookup-api-v1", "1.6.10+93d8cb8290"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
		d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", "fabric-client-tags-api-v1", "1.0.2+b35fea8390"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
		d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", "fabric-command-api-v2", "2.1.7+0c17ea9690"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
		d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", "fabric-content-registries-v0", "3.3.1+624e468e90"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
		d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", "fabric-convention-tags-v1", "1.1.1+7cd20a1490"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
		d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", "fabric-crash-report-info-v1", "0.2.5+aeb40ebe90"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
		d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", "fabric-dimensions-v1", "2.1.30+aeb40ebe90"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
		d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", "fabric-entity-events-v1", "1.4.18+9ff28f4090"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
		d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", "fabric-events-interaction-v0", "0.4.28+aeb40ebe90"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
		d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", "fabric-game-rule-api-v1", "1.0.21+aeb40ebe90"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
		d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", "fabric-key-binding-api-v1", "1.0.21+93d8cb8290"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
		d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", "fabric-lifecycle-events-v1", "2.1.2+aeb40ebe90"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
		d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", "fabric-message-api-v1", "5.0.3+176380a290"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
		d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", "fabric-networking-api-v1", "1.2.4+5eb68ef290"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
		d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", "fabric-object-builder-api-v1", "4.0.12+93d8cb8290"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
		d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", "fabric-particles-v1", "1.0.11+79adfe0a90"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
		d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", "fabric-registry-sync-v0", "0.9.26+c6af733c90"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
		d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", "fabric-resource-loader-v0", "0.6.1+aeb40ebe90"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
		d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", "fabric-screen-api-v1", "1.0.27+93d8cb8290"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
		d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", "fabric-textures-v0", "1.0.21+aeb40ebe90"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);

		d.addMaven("https://maven.shedaniel.me", new MavenId("me.shedaniel.cloth", "cloth-config-fabric", "8.2.88"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
		d.addMaven("https://maven.shedaniel.me", new MavenId("me.shedaniel.cloth", "basic-math", "0.6.1"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);
		d.addMaven("https://api.modrinth.com/maven/", new MavenId("maven.modrinth", "modmenu", "4.0.6"), ModDependencyFlag.RUNTIME, ModDependencyFlag.COMPILE);

		d.addMaven("https://api.modrinth.com/maven/", new MavenId("maven.modrinth", "lazydfu", Properties.LAZY_DFU), ModDependencyFlag.RUNTIME);
		d.addMaven("https://api.modrinth.com/maven/", new MavenId("maven.modrinth", "ferrite-core", "5.0.0-fabric"), ModDependencyFlag.RUNTIME);
		d.addMaven("https://api.modrinth.com/maven/", new MavenId("maven.modrinth", "starlight", "1.1.1+1.19"), ModDependencyFlag.RUNTIME);
		d.addMaven("https://api.modrinth.com/maven/", new MavenId("maven.modrinth", "lithium", "mc1.19.2-0.8.3"), ModDependencyFlag.RUNTIME);
	}

	@Override
	public int getJavaVersion() {
		return 17;
	}

	@Override
	public BrachyuraDecompiler decompiler() {
		return new FernflowerDecompiler(Maven.getMavenJarDep(QuiltMaven.URL, new MavenId("org.quiltmc", "quiltflower", Properties.QUILTFLOWER)));
	}
	
	@Override
	public ProcessorChain resourcesProcessingChain() {
		return new ProcessorChain(super.resourcesProcessingChain(), new FmjVersionFixer(this));
	}
}
