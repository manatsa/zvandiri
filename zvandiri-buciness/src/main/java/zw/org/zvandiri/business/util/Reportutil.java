package zw.org.zvandiri.business.util;

import zw.org.zvandiri.business.util.dto.SearchDTO;

import javax.persistence.Query;

public class Reportutil {

    public static final Integer commonPatientQuery(StringBuilder builder,SearchDTO dto, int position) {
        //builder.append() "Select Distinct p from Patient p  ";

        if (dto.getSearch(dto)) {
            builder.append(" where ");
            if (dto.getProvince() != null) {
                if (position == 0) {
                    builder.append("p.primaryClinic.district.province=:province");
                    position++;
                } else {
                    builder.append(" and p.primaryClinic.district.province=:province");
                }
            }

            if (dto.getFacilities() != null && !dto.getFacilities().isEmpty()) {
                //System.err.println("################# DTO facilities is not null");
                if (position == 0) {
                    builder.append(" p.primaryClinic in :facilities");
                    position++;
                } else {
                    builder.append(" and p.primaryClinic in :facilities");
                }
            } else if (dto.getDistricts() != null && !dto.getDistricts().isEmpty()) {
                //System.err.println("$$$$$$$$$$$$$$$$$$$$$$$ DTO districts is not null");
                if (position == 0) {
                    builder.append(" p.primaryClinic.district in :districts");
                    position++;
                } else {
                    builder.append(" and p.primaryClinic.district in :districts");
                }
            } else if (dto.getProvinces() != null && !dto.getProvinces().isEmpty()) {
                //System.err.println("^^^^^^^^^^^^^^^^^^^^^^^ DTO provinces is not null");
                if (position == 0) {
                    builder.append(" p.primaryClinic.district.province in :provinces");
                    position++;
                } else {
                    builder.append(" and p.primaryClinic.district.province in :provinces");
                }
            }


            if (dto.getDistrict() != null) {
                if (position == 0) {
                    builder.append(" p.primaryClinic.district=:district");
                    position++;
                } else {
                    builder.append(" and p.primaryClinic.district=:district");
                }
            }
            if (dto.getPrimaryClinic() != null) {
                if (position == 0) {
                    builder.append("p.primaryClinic=:primaryClinic");
                    position++;
                } else {
                    builder.append(" and p.primaryClinic=:primaryClinic");
                }
            }
            if (dto.getSupportGroup() != null) {
                if (position == 0) {
                    builder.append("p.supportGroup=:supportGroup");
                    position++;
                } else {
                    builder.append(" and p.supportGroup=:supportGroup");
                }
            }
            if (dto.getGender() != null) {
                if (position == 0) {
                    builder.append("p.gender=:gender");
                    position++;
                } else {
                    builder.append(" and p.gender=:gender");
                }
            }

            if (dto.getStatus() != null) {
                if (position == 0) {
                    builder.append(" p.status=:status");
                    position++;
                } else {
                    builder.append(" and p.status=:status");
                }
            }
        }

        return position;
    }

    public static final Integer commonCadreQuery(StringBuilder builder,SearchDTO dto, int position) {

        if (dto.getSearch(dto)) {
            builder.append(" where ");
            if (dto.getProvince() != null) {
                if (position == 0) {
                    builder.append("p.province=:province");
                    position++;
                } else {
                    builder.append(" and p.province=:province");
                }
            }

            if (dto.getFacilities() != null && !dto.getFacilities().isEmpty()) {
                //System.err.println("################# DTO facilities is not null");
                if (position == 0) {
                    builder.append(" p.primaryClinic in :facilities");
                    position++;
                } else {
                    builder.append(" and p.primaryClinic in :facilities");
                }
            } else if (dto.getDistricts() != null && !dto.getDistricts().isEmpty()) {
                //System.err.println("$$$$$$$$$$$$$$$$$$$$$$$ DTO districts is not null");
                if (position == 0) {
                    builder.append(" p.district in :districts");
                    position++;
                } else {
                    builder.append(" and p.district in :districts");
                }
            } else if (dto.getProvinces() != null && !dto.getProvinces().isEmpty()) {
                //System.err.println("^^^^^^^^^^^^^^^^^^^^^^^ DTO provinces is not null");
                if (position == 0) {
                    builder.append(" p.province in :provinces");
                    position++;
                } else {
                    builder.append(" and p.province in :provinces");
                }
            }


            if (dto.getDistrict() != null) {
                if (position == 0) {
                    builder.append(" p.district=:district");
                    position++;
                } else {
                    builder.append(" and p.district=:district");
                }
            }
            if (dto.getPrimaryClinic() != null) {
                if (position == 0) {
                    builder.append("p.primaryClinic=:primaryClinic");
                    position++;
                } else {
                    builder.append(" and p.primaryClinic=:primaryClinic");
                }
            }
            if (dto.getSupportGroup() != null) {
                if (position == 0) {
                    builder.append("p.supportGroup=:supportGroup");
                    position++;
                } else {
                    builder.append(" and p.supportGroup=:supportGroup");
                }
            }
            if (dto.getGender() != null) {
                if (position == 0) {
                    builder.append("p.gender=:gender");
                    position++;
                } else {
                    builder.append(" and p.gender=:gender");
                }
            }

            if (dto.getStatus() != null) {
                if (position == 0) {
                    builder.append(" p.status=:status");
                    position++;
                } else {
                    builder.append(" and p.status=:status");
                }
            }
        }

        return position;
    }

    public static final Query commonQueryParams(Query query, SearchDTO dto) {

        if (dto.getProvince() != null) {
            query.setParameter("province", dto.getProvince());
        }

        if (dto.getFacilities() != null && !dto.getFacilities().isEmpty()) {
            query.setParameter("facilities", dto.getFacilities());
        } else if (dto.getDistricts() != null && !dto.getDistricts().isEmpty()) {
            query.setParameter("districts", dto.getDistricts());
        }else if (dto.getProvinces() != null && !dto.getProvinces().isEmpty()) {
            query.setParameter("provinces", dto.getProvinces());
        }

        if (dto.getDistrict() != null) {
            query.setParameter("district", dto.getDistrict());
        }
        if (dto.getPrimaryClinic() != null) {
            query.setParameter("primaryClinic", dto.getPrimaryClinic());
        }
        if (dto.getSupportGroup() != null) {
            query.setParameter("supportGroup", dto.getSupportGroup());
        }
        if (dto.getGender() != null) {
            query.setParameter("gender", dto.getGender());
        }
        if (dto.getStatus() != null) {
            query.setParameter("status", dto.getStatus());
        }

        return query;
    }
}
