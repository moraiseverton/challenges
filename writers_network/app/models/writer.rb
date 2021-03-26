class Writer < ApplicationRecord
    default_scope { order(name: :asc) }
    validates :name, :website, presence: true

    has_and_belongs_to_many(:writer,
      :join_table => :friendships,
      :foreign_key => :writer_a_id,
      :association_foreign_key => :writer_b_id)

    def self.list_writers_except writer_to_remove
      return Writer.where.not(id: writer_to_remove.id)
    end
  
    def self.list_by_ids writer_ids
      return Writer.where(id: writer_ids)
    end
  
    def self.find_writer writer_id
      return Writer.joins("INNER JOIN friendships ON friendships.writer_a_id = writers.id OR friendships.writer_b_id = writers.id").where(id: writer_id).first
    end
end
