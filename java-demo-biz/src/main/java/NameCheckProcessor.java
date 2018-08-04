import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementScanner8;
import java.util.Set;

/**
 * Created by xupingmao on 2017/8/9.
 */
@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class NameCheckProcessor extends AbstractProcessor {

    NameChecker checker = new NameChecker();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (Element element : roundEnv.getRootElements()) {
            checkName(element);
        }

        return false;
    }

    private void checkName(Element element) {
        System.out.println(element.getSimpleName());
        checker.scan(element);
    }

    class NameChecker extends ElementScanner8<Void, Void> {

        @Override
        public Void visitType(TypeElement e, Void aVoid) {
            System.out.println(String.format("visitType %s", e.getQualifiedName()));
            return super.visitType(e, aVoid);
        }

        @Override
        public Void visitVariable(VariableElement e, Void aVoid) {
            System.out.println(String.format("visitVariable %s", e.getSimpleName()));
            return super.visitVariable(e, aVoid);
        }

        @Override
        public Void visitExecutable(ExecutableElement e, Void aVoid) {
            System.out.println(String.format("visitExecutable %s", e.getSimpleName()));
            return super.visitExecutable(e, aVoid);
        }
    }
}
