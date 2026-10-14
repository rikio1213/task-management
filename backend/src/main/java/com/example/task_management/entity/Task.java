package com.example.task_management.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * タスクエンティティ
 * このクラスが DB の "task" テーブルに対応する。
 * @Entity をつけると Spring Boot が自動でテーブルを作成してくれる。
 */
@Entity
@Data                      // Lombok: getter/setter/toString を自動生成
@Table(name = "task")      // テーブル名を "task" に指定
public class Task {

    // PRIMARY KEY。AUTO_INCREMENT で自動採番する。
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // タスクのタイトル。NULL は許可しない。
    @Column(nullable = false)
    private String title;

    // 進捗ステータス。EnumType.STRING で "TODO" などの文字列でDBに保存する。
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.TODO;  // デフォルトは "未着手"

    // 作成日時。登録後は変更しない（updatable = false）。
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 最終更新日時。
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // ステータスの選択肢を Enum で定義する
    public enum Status {
        TODO,        // 未着手
        IN_PROGRESS, // 進行中
        DONE         // 完了
    }

    // DB に INSERT される直前に自動で呼ばれる。日時を自動セットする。
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    // DB に UPDATE される直前に自動で呼ばれる。更新日時を自動セットする。
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
