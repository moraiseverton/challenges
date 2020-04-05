class Writer < ApplicationRecord
    validates :name, :website, presence: true

    has_and_belongs_to_many(:writer,
      :join_table => :friendships,
      :foreign_key => :writer_a_id,
      :association_foreign_key => :writer_b_id)

    def self.list_writers_except writer_to_remove
      return Writer.where.not(id: writer_to_remove.id)
    end
end
