package ink.fengshuai.minio.autoconfigure.objectargs;

import java.util.concurrent.TimeUnit;

public record Duration(TimeUnit timeUnit, int value) {
}
